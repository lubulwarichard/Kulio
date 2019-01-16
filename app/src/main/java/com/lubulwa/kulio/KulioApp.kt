package com.lubulwa.kulio

import android.app.Application
import timber.log.Timber

class KulioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree());
    }

}