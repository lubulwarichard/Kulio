package com.lubulwa.kulio.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlightsResponse(
    @SerializedName("ScheduleResource")
    @Expose
    val scheduleResource: ScheduleResource
)

data class ScheduleResource(
    @SerializedName("Schedule")
    @Expose
    val schedule: ArrayList<Schedule>
)

data class Schedule(
    @SerializedName("Flight")
    @Expose
    val flights: ArrayList<Flight>
)

data class Flight(
    @SerializedName("Departure")
    @Expose
    val departure: Departure,

    @SerializedName("Arrival")
    @Expose
    val arrival: Arrival

): Serializable {
    data class Departure(
        @SerializedName("AirportCode")
        @Expose
        val airportCode: String,

        @SerializedName("ScheduledTimeLocal")
        @Expose
        val scheduledTimeLocal: ScheduledTimeLocal
    ) : Serializable {
        data class ScheduledTimeLocal(
            @SerializedName("DateTime")
            @Expose
            val dateTime: String
        ) : Serializable
    }

    data class Arrival(
        @SerializedName("AirportCode")
        @Expose
        val airportCode: String,

        @SerializedName("ScheduledTimeLocal")
        @Expose
        val scheduledTimeLocal: Departure.ScheduledTimeLocal
    ) : Serializable
}