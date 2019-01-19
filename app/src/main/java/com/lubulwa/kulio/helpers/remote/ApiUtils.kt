package com.lubulwa.kulio.helpers.remote

import com.lubulwa.kulio.remote.ApiService
import com.lubulwa.kulio.remote.ApiClient

object ApiUtils {

    val BASE_URL = "https://api.lufthansa.com/v1/"

    fun getAPIService(shouldAuthorize: Boolean): ApiService {
        return ApiClient.getClient(BASE_URL, shouldAuthorize)
            .create(ApiService::class.java)
    }

}