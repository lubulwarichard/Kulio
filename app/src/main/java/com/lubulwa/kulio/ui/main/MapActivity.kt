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
import com.lubulwa.kulio.model.AirportResponse
import com.lubulwa.kulio.model.Flight
import com.lubulwa.kulio.presenter.MapPresenter
import com.lubulwa.kulio.ui.interfaces.MapContract

class MapActivity : BaseActivity(), OnMapReadyCallback, MapContract.View, GoogleMap.OnMarkerClickListener {

    private lateinit var passedFlightItem: Flight

    lateinit var mMap: GoogleMap

    lateinit var progressDialog: ProgressDialog

    lateinit var mapPresenter: MapPresenter

    lateinit var originPosition: LatLng
    lateinit var destPosition: LatLng

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

        mapPresenter.getAirportLocationOrigin(passedFlightItem.departure.airportCode)
        mapPresenter.getAirportLocationDestination(passedFlightItem.arrival.airportCode)
    }

    private fun setupViews() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setOnMarkerClickListener (this)
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

    override fun onGetAirportLocStarted() {

    }

    override fun onGetAirportLocSuccess(airportResponse: AirportResponse, isOrigin: Boolean) {
        val position = LatLng(
            airportResponse.airportResource.airports.airportSingle.position.coordinate.latitude,
            airportResponse.airportResource.airports.airportSingle.position.coordinate.longitude
        )

        if (isOrigin) {
            originPosition = position
        } else {
            destPosition = position
        }

        addMapMarker(
            position,
            airportResponse.airportResource.airports.airportSingle.airportCode
        )
    }

    override fun onGetAirportLocFailed(errorMessage: String, errorCode: Int) {

    }

}
