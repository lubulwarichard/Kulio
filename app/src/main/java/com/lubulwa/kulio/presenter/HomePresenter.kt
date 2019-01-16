package com.lubulwa.kulio.presenter

import android.annotation.SuppressLint
import com.lubulwa.kulio.remote.ApiService
import com.lubulwa.kulio.helpers.remote.ApiUtils
import com.lubulwa.kulio.model.FlightsResponse
import com.lubulwa.kulio.ui.interfaces.HomeContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomePresenter(var view: HomeContract.View) : HomeContract.Presenter {


    var mAPIService: ApiService = ApiUtils.getAPIService(true)

    /**
     * Method to search scheduled flights
     * @param origin airport of origin
     * @param destination airport where user is going
     * @param dateTime time when user is leaving
     */
    @SuppressLint("CheckResult")
    override fun findScheduledFlights(origin: String, destination: String, dateTime: String) {

        view.findScheduledFlightsStarted()

        mAPIService.getFlights(origin, destination, dateTime, 40, 0)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<FlightsResponse>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<FlightsResponse>) {
                    if (response.isSuccessful) {
                        view.findScheduledFlightsSuccess(response.body())
                    } else {
                        view.findScheduledFlightsFailed(response.message(), response.code())
                    }
                }

                override fun onError(e: Throwable) {
                    view.findScheduledFlightsFailed(e.localizedMessage, 0)
                }


            })

    }

}