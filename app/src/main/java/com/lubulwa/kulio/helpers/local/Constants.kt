package com.lubulwa.kulio.helpers.local

import com.lubulwa.kulio.BuildConfig
import com.lubulwa.kulio.R

object Constants {

    val api_key = BuildConfig.LufthansaApiKey
    val api_secret = BuildConfig.LufthansaSecretKey
    val grant_type = "client_credentials"
    var access_token = ""

    val SELECT_AIRPORT_CAT_INTENT_DATA = "select_airport_cat_intent_data"

    val AIRPORTS_INTENT_FILTER = "airports_intent_filter"
    val AIRPORTS_INTENT_DATA = "airports_intent_data"

    val FLIGHT_INTENT_DATA = "flight_intent_data"
    val ORIGIN_AIRPORT_INTENT_DATA = "origin_airport_intent_data"
    val DEST_AIRPORT_INTENT_DATA = "dest_airport_intent_data"
    val DEPART_DATE_INTENT_DATA = "depart_date_intent_data"

}