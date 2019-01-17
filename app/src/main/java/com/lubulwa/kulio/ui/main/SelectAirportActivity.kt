package com.lubulwa.kulio.ui.main

import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.model.Airport
import com.lubulwa.kulio.model.AirportResponse
import com.lubulwa.kulio.presenter.SelectAirportPresenter
import com.lubulwa.kulio.ui.interfaces.SelectAirportContract
import dance.krantz.com.danceapp.adapters.AirportsAdapter
import kotlinx.android.synthetic.main.activity_select_airport.*
import android.support.v4.content.LocalBroadcastManager
import android.content.Intent
import android.content.BroadcastReceiver
import com.lubulwa.kulio.helpers.local.Constants


class SelectAirportActivity : BaseActivity(), SelectAirportContract.View, AirportsAdapter.ItemListener {

    lateinit var selectAirportPresenter: SelectAirportPresenter
    lateinit var airportsAdapter: AirportsAdapter

    lateinit var airportsArrayList: ArrayList<Airport>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_airport)

        setupToolbar()
        initStuff()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.getBooleanExtra(Constants.SELECT_AIRPORT_CAT_INTENT_DATA, true)) {
            supportActionBar!!.title = getString(R.string.select_origin_airport)
        } else {
            supportActionBar!!.title = getString(R.string.select_dest_airport)
        }
    }

    private fun initStuff() {

        selectAirportPresenter = SelectAirportPresenter(this)

        selectAirportPresenter.searchAirports()

        val layoutManager = LinearLayoutManager(this);
        rv_airports.layoutManager = layoutManager;

        val mDividerItemDecoration = DividerItemDecoration(
            rv_airports.context,
            layoutManager.orientation
        )
        rv_airports.addItemDecoration(mDividerItemDecoration)
    }

    override fun searchAirportsStarted() {

    }

    override fun searchAirportsSuccess(airportResponse: AirportResponse) {
        pb_loading_airports.visibility = View.GONE
        airportsArrayList = airportResponse.airportResource.airports.airport

        airportsAdapter = AirportsAdapter(this, airportsArrayList, this)
        rv_airports.adapter = airportsAdapter
    }

    override fun searchAirportsFailed(errorMessage: String, errorCode: Int) {
        pb_loading_airports.visibility = View.GONE
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onAirportItemClicked(airportItem: Airport) {
        val intent = Intent(Constants.AIRPORTS_INTENT_FILTER)
        intent.putExtra(Constants.AIRPORTS_INTENT_DATA, airportItem)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

}
