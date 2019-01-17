package com.lubulwa.kulio.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AirportResponse(
    @SerializedName("AirportResource")
    @Expose
    val airportResource: AirportResource
)

data class AirportResource(
    @SerializedName("Airports")
    @Expose
    val airports: Airports
)

data class Airports(
    @SerializedName("Airport")
    @Expose
    val airport: ArrayList<Airport>,

    @SerializedName("Airport")
    @Expose
    val airportSingle: Airport
)

data class Airport(
    @SerializedName("AirportCode")
    @Expose
    val airportCode: String,

    @SerializedName("Position")
    @Expose
    val position: Position,

    @SerializedName("CityCode")
    @Expose
    val cityCode: String,

    @SerializedName("CountryCode")
    @Expose
    val countryCode: String,

    @SerializedName("Names")
    @Expose
    val names: Names
): Serializable {
    data class Position(
        @SerializedName("Coordinate")
        @Expose
        val coordinate: Coordinate
    ) {
        data class Coordinate(
            @SerializedName("Latitude")
            @Expose
            val latitude: Double,

            @SerializedName("Longitude")
            @Expose
            val longitude: Double
        )
    }
}

data class Names(
    @SerializedName("Name")
    @Expose
    val name: Name
)

data class Name(
    @SerializedName("@LanguageCode")
    @Expose
    val languageCode: String,

    @SerializedName("$")
    @Expose
    val airportName: String
)