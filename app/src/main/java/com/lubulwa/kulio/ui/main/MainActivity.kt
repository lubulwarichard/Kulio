package com.lubulwa.kulio.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.helpers.local.KulioUtlis
import com.lubulwa.kulio.model.Airport
import com.lubulwa.kulio.model.Flight
import com.lubulwa.kulio.model.FlightsResponse
import com.lubulwa.kulio.presenter.HomePresenter
import com.lubulwa.kulio.ui.component.FlightSchedulesAdapter
import com.lubulwa.kulio.ui.interfaces.HomeContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), HomeContract.View, FlightSchedulesAdapter.ItemListener {

    private var isRequestingOriginAirport: Boolean = true
    private var lbm: LocalBroadcastManager? = null

    lateinit var homePresenter: HomePresenter
    private var flightSchedulesAdapter: FlightSchedulesAdapter? = null

    lateinit var flightsArrayList: ArrayList<Flight>

    var originAirport: Airport? = null
    var destinationAirport: Airport? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initStuff()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initStuff() {
        homePresenter = HomePresenter(this)

        val lbm = LocalBroadcastManager.getInstance(this)
        lbm.registerReceiver(receiver, IntentFilter(Constants.AIRPORTS_INTENT_FILTER));

        layout_origin_airport.setOnClickListener {
            isRequestingOriginAirport = true
            val intent = Intent(this, SelectAirportActivity::class.java)
            intent.putExtra(Constants.SELECT_AIRPORT_CAT_INTENT_DATA, isRequestingOriginAirport)
            startActivity(intent)
        }

        layout_destination_airport.setOnClickListener {
            isRequestingOriginAirport = false
            val intent = Intent(this, SelectAirportActivity::class.java)
            intent.putExtra(Constants.SELECT_AIRPORT_CAT_INTENT_DATA, isRequestingOriginAirport)
            startActivity(intent)
        }

        search_flights_button.setOnClickListener {
            if (originAirport != null && destinationAirport != null) {
                if (flightSchedulesAdapter != null) {
                    flightSchedulesAdapter!!.mValues.clear()
                    flightSchedulesAdapter!!.notifyDataSetChanged()
                }
                homePresenter.findScheduledFlights(
                    originAirport!!.airportCode,
                    destinationAirport!!.airportCode,
                    KulioUtlis.getTomorrowsDate()
                )
            } else {
                Toast.makeText(this, getString(R.string.select_origin_dest), Toast.LENGTH_LONG).show()
            }
        }

        val layoutManager = LinearLayoutManager(this);
        rv_schedules.layoutManager = layoutManager;

        val mDividerItemDecoration = DividerItemDecoration(
            rv_schedules.context,
            layoutManager.orientation
        )
        rv_schedules.addItemDecoration(mDividerItemDecoration)

    }

    override fun findScheduledFlightsStarted() {
        pb_loading_schedules.visibility = View.VISIBLE
    }

    override fun findScheduledFlightsSuccess(flightsResponse: FlightsResponse?) {
        pb_loading_schedules.visibility = View.GONE
        flightsArrayList = flightsResponse!!.scheduleResource.schedule.get(0).flights

        flightSchedulesAdapter = FlightSchedulesAdapter(this, flightsArrayList, this)
        rv_schedules.adapter = flightSchedulesAdapter
    }

    override fun findScheduledFlightsFailed(errorMessage: String, errorCode: Int) {
        pb_loading_schedules.visibility = View.GONE
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent != null) {
                val airportItem = intent.getSerializableExtra(Constants.AIRPORTS_INTENT_DATA) as Airport
                if (isRequestingOriginAirport) {
                    originAirport = airportItem
                    tv_origin_airport.text = airportItem.airportCode
                    tv_origin_airport_name.text = airportItem.names.name.airportName
                } else {
                    destinationAirport = airportItem
                    tv_destination_airport.text = airportItem.airportCode
                    tv_destination_airport_name.text = airportItem.names.name.airportName
                }
            }
        }
    }

    override fun onFlightItemClicked(flightItem: Flight) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra(Constants.FLIGHT_INTENT_DATA, flightItem)
        intent.putExtra(Constants.ORIGIN_AIRPORT_INTENT_DATA, originAirport)
        intent.putExtra(Constants.DEST_AIRPORT_INTENT_DATA, destinationAirport)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (lbm != null) {
            lbm!!.unregisterReceiver(receiver)
        }
    }

}
