package com.lubulwa.kulio.remote

import com.lubulwa.kulio.model.AirportResource
import com.lubulwa.kulio.model.AirportResponse
import com.lubulwa.kulio.model.FlightsResponse
import com.lubulwa.kulio.model.TokenResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("references/airports")
    fun searchAirports(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("LHoperated") lhOperated: String = "0",
        @Query("lang") lang: String = "en"
    ): Observable<Response<AirportResponse>>

    @POST("oauth/token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): Observable<Response<TokenResponse>>

    @Headers("Accept: application/json")
    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getFlights(
        @Path("origin") origin: String,
        @Path("destination") destination: String,
        @Path("fromDateTime") fromDateTime: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("directFlights") directFlights: String = "0"
    ): Observable<Response<FlightsResponse>>

}