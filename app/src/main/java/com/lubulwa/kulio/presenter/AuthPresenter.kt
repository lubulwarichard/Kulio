package com.lubulwa.kulio.presenter

import android.annotation.SuppressLint
import com.lubulwa.kulio.helpers.remote.ApiUtils
import com.lubulwa.kulio.model.TokenResponse
import com.lubulwa.kulio.remote.ApiService
import com.lubulwa.kulio.ui.interfaces.AuthContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber

class AuthPresenter(var view: AuthContract.View) : AuthContract.Presenter {

    var mAPIService: ApiService = ApiUtils.getAPIService(false)

    /**
     * Method to retrieve access token, it will be needed in the every
     * request we make
     * @param clientId Client Id
     * @param clientSecret Client Secret
     * @param grantType Grant Type
     */
    @SuppressLint("CheckResult")
    override fun getToken(clientId: String, clientSecret: String, grantType: String){
        view.getTokenStarted()

        Timber.v("getToken, $clientId")
        mAPIService.getAccessToken(clientId, clientSecret, grantType)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<TokenResponse>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        view.getTokenSuccess(response.body()!!)
                    } else {
                        view.getTokenFailed(response.message(), response.code())
                    }
                }

                override fun onError(e: Throwable) {
                    view.getTokenFailed(e.localizedMessage)
                }

            })

    }
}