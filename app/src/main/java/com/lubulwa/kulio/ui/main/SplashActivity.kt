package com.lubulwa.kulio.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.lubulwa.kulio.R
import com.lubulwa.kulio.base.BaseActivity
import com.lubulwa.kulio.helpers.local.Constants
import com.lubulwa.kulio.helpers.local.KulioUtlis
import com.lubulwa.kulio.model.TokenResponse
import com.lubulwa.kulio.presenter.AuthPresenter
import com.lubulwa.kulio.ui.interfaces.AuthContract
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.os.HandlerCompat.postDelayed



class SplashActivity : BaseActivity(), AuthContract.View {

    lateinit var authPresenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initStuff()
    }

    private fun initStuff() {
        authPresenter = AuthPresenter(this)

        if (KulioUtlis.isNetworkAvailable(this)) {
            authPresenter.getToken(Constants.api_key, Constants.api_secret)
        } else {
            tv_splash_info.text = getString(R.string.network_fail)
        }
    }

    override fun getTokenSuccess(tokenResponse: TokenResponse) {
        Constants.access_token = tokenResponse.access_token
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun getTokenFailed(errorMessage: String, errorCode: Int) {
        Timber.e(errorMessage)
        tv_splash_info.text = getString(R.string.initialization_failed)
    }

}
