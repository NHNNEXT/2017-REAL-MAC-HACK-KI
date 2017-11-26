package com.amigotrip.android

import android.app.Application
import timber.log.Timber

/**
 * Created by Zimincom on 2017. 11. 16..
 */
class AmigoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        UserInfoManager.initManager(this)
        Timber.plant(Timber.DebugTree())
    }
}