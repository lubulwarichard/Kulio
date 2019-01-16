package com.lubulwa.kulio.ui.interfaces

import com.lubulwa.kulio.model.FlightsResponse

interface HomeContract {

    interface View {
        fun findScheduledFlightsStarted()
        fun findScheduledFlightsSuccess(flightsResponse: FlightsResponse?)
        fun findScheduledFlightsFailed(errorMessage: String, errorCode: Int)
    }

    interface Presenter {
        fun findScheduledFlights(origin: String, destination: String, dateTime: String)
    }

}