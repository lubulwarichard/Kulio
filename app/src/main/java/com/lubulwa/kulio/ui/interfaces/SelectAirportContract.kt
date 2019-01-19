package com.lubulwa.kulio.ui.interfaces

import com.lubulwa.kulio.model.AirportResponse

interface SelectAirportContract {

    interface View {
        fun searchAirportsSuccess(airportResponse: AirportResponse)
        fun searchAirportsFailed(errorMessage: String, errorCode: Int = 0)
    }

    interface Presenter {
        fun searchAirports(offset: Int, fetchCount: Int)
    }

}