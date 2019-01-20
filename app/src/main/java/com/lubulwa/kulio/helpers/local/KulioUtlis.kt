package com.lubulwa.kulio.helpers.local

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*


object KulioUtlis {

    /**
     * Check if there is any connectivity
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return cm!!.activeNetworkInfo != null
    }

    @SuppressLint("SimpleDateFormat")
    fun getTomorrowsDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")

        return dateFormat.format(calendar.time).toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromString(source: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")
        val date = dateFormat.parse(source)
        val formatter = SimpleDateFormat("E dd, MMMM")
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeFromDateString(source: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")
        val date = dateFormat.parse(source)
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(date)
    }

    fun getFlightDurationFromString(source: String): String {
        try {
            val timeString = source.split("T")[1]
            val hours = timeString.split("H")[0]+"h"
            val minutes = timeString.split("H")[1].toLowerCase()

            return "$hours $minutes"
        } catch (e: Exception) {
            return source
        }

    }
}