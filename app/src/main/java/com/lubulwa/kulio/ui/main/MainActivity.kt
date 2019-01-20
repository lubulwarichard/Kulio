package com.lubulwa.kulio.ui.main

import android.app.DatePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.model.Airport
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {

    private var isRequestingOriginAirport: Boolean = true
    private var lbm: LocalBroadcastManager? = null

    private var originAirport: Airport? = null
    private var destinationAirport: Airport? = null

    private var departDate: String? = null

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

        depart_date_layout.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    datePicker, year, month, day ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)

                    val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm", Locale.US)

                    departDate = format.format(calendar.time)
                    val new_month = month +1
                    depart_date_tv.text = "$year-$new_month-$day"
                }, year, month, dayOfMonth)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        search_flights_button.setOnClickListener {
            if (originAirport != null && destinationAirport != null) {
                val intent = Intent(this, SearchFlightsActivity::class.java)
                intent.putExtra(Constants.ORIGIN_AIRPORT_INTENT_DATA, originAirport)
                intent.putExtra(Constants.DEST_AIRPORT_INTENT_DATA, destinationAirport)
                intent.putExtra(Constants.DEPART_DATE_INTENT_DATA, departDate)
                startActivity(intent)
            } else {
                Snackbar.make(coordinatorLayout, getString(R.string.select_origin_dest), Snackbar.LENGTH_LONG).show()
            }
        }

        // add background image
        Glide.with(this)
            .load(R.drawable.lufthansa_bg_img)
            .into(iv_home_bg)

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

    override fun onDestroy() {
        super.onDestroy()
        if (lbm != null) {
            lbm!!.unregisterReceiver(receiver)
        }
        finish()
    }

}
