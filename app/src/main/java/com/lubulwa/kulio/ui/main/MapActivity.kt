package com.lubulwa.kulio.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.model.Airport
import com.lubulwa.kulio.model.AirportResponse
import com.lubulwa.kulio.model.Flight
import com.lubulwa.kulio.presenter.MapPresenter
import com.lubulwa.kulio.ui.interfaces.MapContract

class MapActivity : BaseActivity(), OnMapReadyCallback, MapContract.View, GoogleMap.OnMarkerClickListener {

    private lateinit var passedFlightItem: Flight
    private lateinit var passedOriginAirport: Airport
    private lateinit var passedDestAirport: Airport

    lateinit var mMap: GoogleMap

    lateinit var progressDialog: ProgressDialog

    lateinit var mapPresenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setupViews()
        initStuff()
    }

    private fun initStuff() {
        progressDialog = ProgressDialog(this)

        mapPresenter = MapPresenter(this)

        passedFlightItem = intent.getSerializableExtra(Constants.FLIGHT_INTENT_DATA) as Flight
        passedOriginAirport = intent.getSerializableExtra(Constants.ORIGIN_AIRPORT_INTENT_DATA) as Airport
        passedDestAirport = intent.getSerializableExtra(Constants.DEST_AIRPORT_INTENT_DATA) as Airport

    }

    private fun setupViews() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
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
        addMapMarker(originPosition, passedOriginAirport.airportCode)
        addMapMarker(destinationPosition, passedDestAirport.airportCode)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    fun addMapMarker(position: LatLng, name: String) {
        mMap.addMarker(
            MarkerOptions()
            .position(position)
            .title(name)
        )

        val cameraPosition = CameraPosition.Builder()
            .target(position)
            .zoom(15f)
            .build()

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

}
