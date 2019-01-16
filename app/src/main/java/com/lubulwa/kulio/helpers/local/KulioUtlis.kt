package com.lubulwa.kulio.helpers.local

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
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
}