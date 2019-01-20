package com.lubulwa.kulio.ui.interfaces

import com.lubulwa.kulio.model.FlightsResponse
import org.jetbrains.annotations.Nullable

interface HomeContract {

    interface View {
        fun findScheduledFlightsStarted()
        fun findScheduledFlightsSuccess(flightsResponse: FlightsResponse?)
        fun findScheduledFlightsFailed(errorMessage: String, errorCode: Int = 0)
    }

    interface Presenter {
        fun findScheduledFlights(origin: String, destination: String, dateTime: String, offset: Int, fetchCount: Int)
    }

}