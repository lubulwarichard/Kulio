package com.lubulwa.kulio.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.helpers.local.KulioUtlis
import com.lubulwa.kulio.model.Airport
import com.lubulwa.kulio.model.FlightsResponse
import com.lubulwa.kulio.model.Schedule
import com.lubulwa.kulio.presenter.SearchFlightsPresenter
import com.lubulwa.kulio.ui.component.FlightSchedulesAdapter
import com.lubulwa.kulio.ui.component.InfiniteScrollListener
import com.lubulwa.kulio.ui.interfaces.HomeContract
import kotlinx.android.synthetic.main.activity_search_flights.*
import java.util.ArrayList

class SearchFlightsActivity : BaseActivity(), HomeContract.View, FlightSchedulesAdapter.ItemListener {

    private lateinit var searchFlightsPresenter: SearchFlightsPresenter
    private lateinit var flightSchedulesAdapter: FlightSchedulesAdapter

    private var schedulesArrayList: ArrayList<Schedule> = ArrayList()

    private lateinit var passedOriginAirport: Airport
    private lateinit var passedDestinationAirport: Airport
    private var passedDepartDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_flights)

        initToolbar()
        initStuff()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initStuff() {
        searchFlightsPresenter = SearchFlightsPresenter(this)

        //get intent data
        passedOriginAirport = intent.getSerializableExtra(Constants.ORIGIN_AIRPORT_INTENT_DATA) as Airport
        passedDestinationAirport = intent.getSerializableExtra(Constants.DEST_AIRPORT_INTENT_DATA) as Airport
        passedDepartDate = intent.getStringExtra(Constants.DEPART_DATE_INTENT_DATA)

        tv_origin_airport.text = passedOriginAirport.airportCode
        tv_destination_airport.text = passedDestinationAirport.airportCode

        val layoutManager = LinearLayoutManager(this);
        rv_schedules.layoutManager = layoutManager

        flightSchedulesAdapter = FlightSchedulesAdapter(this, schedulesArrayList, this)
        rv_schedules.adapter = flightSchedulesAdapter

        fetchScheduledFlights()

        rv_schedules.addOnScrollListener(object : InfiniteScrollListener(){

            override fun loadMore() {
                Toast.makeText(this@SearchFlightsActivity, getString(R.string.list_load_more_msg), Toast.LENGTH_SHORT).show()
                fetchScheduledFlights()
            }

        })
    }

    fun fetchScheduledFlights() {
        val offset = rv_schedules.adapter!!.itemCount
        searchFlightsPresenter.findScheduledFlights(
            passedOriginAirport.airportCode,
            passedDestinationAirport.airportCode,
            ((if (passedDepartDate == null) KulioUtlis.getTomorrowsDate() else passedDepartDate)!!),
            offset,
            20
        )
    }

    override fun findScheduledFlightsStarted() {
        pb_loading_schedules.visibility = View.VISIBLE
    }

    override fun findScheduledFlightsSuccess(flightsResponse: FlightsResponse?) {
        pb_loading_schedules.visibility = View.GONE
        schedulesArrayList.addAll(flightsResponse!!.scheduleResource.schedule)

        flightSchedulesAdapter.notifyDataSetChanged()
    }

    override fun findScheduledFlightsFailed(errorMessage: String, errorCode: Int) {
        pb_loading_schedules.visibility = View.GONE
        Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun onScheduleItemClicked(scheduleItem: Schedule) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra(Constants.ORIGIN_AIRPORT_INTENT_DATA, passedOriginAirport)
        intent.putExtra(Constants.DEST_AIRPORT_INTENT_DATA, passedDestinationAirport)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

}
