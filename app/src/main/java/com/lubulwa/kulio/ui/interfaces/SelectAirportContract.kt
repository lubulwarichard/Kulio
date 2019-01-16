package com.lubulwa.kulio.ui.interfaces

import com.lubulwa.kulio.model.AirportResponse

interface SelectAirportContract {

    interface View {
        fun searchAirportsStarted()
        fun searchAirportsSuccess(airportResponse: AirportResponse)
        fun searchAirportsFailed(errorMessage: String, errorCode: Int)
    }

    interface Presenter {
        fun searchAirports()
    }

}