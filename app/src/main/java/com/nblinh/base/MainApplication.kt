package com.nblinh.base

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        } else {
            plant(ReleaseTree())
        }

        Timber.d(BuildConfig.BASE_URL)
    }
}