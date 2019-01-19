package com.lubulwa.kulio.presenter

import android.annotation.SuppressLint
import com.lubulwa.kulio.helpers.remote.ApiUtils
import com.lubulwa.kulio.model.AirportResponse
import com.lubulwa.kulio.remote.ApiService
import com.lubulwa.kulio.ui.interfaces.SelectAirportContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SelectAirportPresenter(var view: SelectAirportContract.View) : SelectAirportContract.Presenter {

    var mAPIService: ApiService = ApiUtils.getAPIService(true)

    /**
     * Method to search airports available
     */
    @SuppressLint("CheckResult")
    override fun searchAirports(){

        mAPIService.searchAirports(40, 0)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<AirportResponse>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<AirportResponse>) {
                    if (response.isSuccessful) {
                        view.searchAirportsSuccess(response.body()!!)
                    } else {
                        view.searchAirportsFailed(response.message(), response.code())
                    }
                }

                override fun onError(e: Throwable) {
                    view.searchAirportsFailed(e.localizedMessage)
                }


            })

    }

}