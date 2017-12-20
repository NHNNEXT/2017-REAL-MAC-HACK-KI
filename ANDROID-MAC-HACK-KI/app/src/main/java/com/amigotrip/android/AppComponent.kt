package com.amigotrip.android

import com.amigotrip.android.module.NetworkModule
import com.amigotrip.android.remote.AmigoService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Zimincom on 2017. 12. 13..
 */
@Singleton
@Component (
        modules = arrayOf(
                NetworkModule::class
        )
)
interface AppComponent {

    fun app() : AmigoApplication
    fun service() : AmigoService

}