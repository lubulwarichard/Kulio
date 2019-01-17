package com.lubulwa.kulio.presenter

import android.annotation.SuppressLint
import com.lubulwa.kulio.helpers.remote.ApiUtils
import com.lubulwa.kulio.model.AirportResponse
import com.lubulwa.kulio.remote.ApiService
import com.lubulwa.kulio.ui.interfaces.MapContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MapPresenter(var view: MapContract.View) : MapContract.Presenter {

    override fun getAirportLocationOrigin(airportCode: String) {
        getAirportLocation(airportCode, true)
    }

    override fun getAirportLocationDestination(airportCode: String) {
        getAirportLocation(airportCode, false)
    }

    var mAPIService: ApiService = ApiUtils.getAPIService(true)

    @SuppressLint("CheckResult")
    fun getAirportLocation(airportCode: String, isOrigin: Boolean) {
        view.onGetAirportLocStarted()

        mAPIService.searchAirport(airportCode, 40, 0)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<AirportResponse>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<AirportResponse>) {
                    if (response.isSuccessful) {
                        view.onGetAirportLocSuccess(response.body()!!, isOrigin)
                    } else {
                        view.onGetAirportLocFailed(response.message(), response.code())
                    }
                }

                override fun onError(e: Throwable) {
                    view.onGetAirportLocFailed(e.localizedMessage, 0)
                }


            })
    }
}