package com.lubulwa.kulio.ui.interfaces

import com.lubulwa.kulio.model.AirportResponse

interface MapContract {

    interface View {
        fun onGetAirportLocStarted()
        fun onGetAirportLocSuccess(airportResponse: AirportResponse, isOrigin: Boolean)
        fun onGetAirportLocFailed(errorMessage: String, errorCode: Int)
    }

    interface Presenter {
        fun getAirportLocationOrigin(airportCode: String)
        fun getAirportLocationDestination(airportCode: String)
    }

}