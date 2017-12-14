package com.amigotrip.android.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Zimincom on 2017. 12. 13..
 */
@Module
class AppModule (val app: Application){

    @Provides @Singleton
    fun provideApp() = app
}