package com.lubulwa.kulio.helpers.remote

import com.lubulwa.kulio.remote.ApiService
import com.lubulwa.kulio.remote.RetrofitClient

object ApiUtils {

    val BASE_URL = "https://api.lufthansa.com/v1/"

    fun getAPIService(shouldAuthorize: Boolean): ApiService {
        return RetrofitClient.getClient(BASE_URL, shouldAuthorize)
            .create(ApiService::class.java)
    }

}