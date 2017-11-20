package com.amigotrip.android

import android.app.Application

/**
 * Created by Zimincom on 2017. 11. 16..
 */
class AmigoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        UserInfoManager.initManager(this)
    }
}