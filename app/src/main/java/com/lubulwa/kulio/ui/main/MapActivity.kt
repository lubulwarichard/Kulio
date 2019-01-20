package com.lubulwa.kulio.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.model.Airport
import com.lubulwa.kulio.model.Flight
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var passedOriginAirport: Airport
    private lateinit var passedDestAirport: Airport

    lateinit var mMap: GoogleMap

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setupViews()
        initStuff()
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = passedOriginAirport.airportCode +" - "+ passedDestAirport.airportCode
    }

    private fun setupViews() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun initStuff() {
        progressDialog = ProgressDialog(this)

        passedOriginAirport = intent.getSerializableExtra(Constants.ORIGIN_AIRPORT_INTENT_DATA) as Airport
        passedDestAirport = intent.getSerializableExtra(Constants.DEST_AIRPORT_INTENT_DATA) as Airport

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setOnMarkerClickListener (this)

        val originPosition = LatLng(
            passedOriginAirport.position.coordinate.latitude,
            passedOriginAirport.position.coordinate.longitude
        )

        val destinationPosition = LatLng(
            passedDestAirport.position.coordinate.latitude,
            passedDestAirport.position.coordinate.longitude
        )

        val latLngBounds = LatLngBounds.Builder()
            .include(originPosition)
            .include(destinationPosition)
            .build()

        addMapMarker(originPosition, passedOriginAirport.names.name.airportName)
        addMapMarker(destinationPosition, passedDestAirport.names.name.airportName)

        mMap.addPolyline(PolylineOptions()
            .add(originPosition, destinationPosition)
            .width(5f)
            .color(R.color.colorPrimary))

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding))
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    private fun addMapMarker(position: LatLng, name: String) {
        mMap.addMarker(
            MarkerOptions()
            .position(position)
            .title(name)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

}
