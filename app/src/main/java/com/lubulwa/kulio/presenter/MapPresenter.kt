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

    var mAPIService: ApiService = ApiUtils.getAPIService(true)

}