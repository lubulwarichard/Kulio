package com.lubulwa.kulio.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lubulwa.kulio.R
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.model.Flight

class MapActivity : AppCompatActivity() {

    private lateinit var passedFlightItem: Flight

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initStuff()
    }

    private fun initStuff() {
        passedFlightItem = intent.getSerializableExtra(Constants.FLIGHT_INTENT_DATA) as Flight
    }
}
