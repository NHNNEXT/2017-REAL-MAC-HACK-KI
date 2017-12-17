package com.amigotrip.android.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Zimincom on 2017. 12. 13..
 */
@Module
class ContextModule (private val context: Context){

    @Provides
    fun context() : Context {
        return context
    }
}