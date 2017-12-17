package com.amigotrip.android

import android.app.Application
import com.amigotrip.android.module.ContextModule
import com.amigotrip.android.module.NetworkModule
import com.amigotrip.android.remote.AmigoService
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Zimincom on 2017. 11. 16..
 */

class AmigoApplication @Inject constructor()  : Application(){

    companion object {
        lateinit var amigoService: AmigoService
    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        UserInfoManager.initManager(this)
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)

        component = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .networkModule(NetworkModule())
                .build()

        amigoService = component.service()
    }

}