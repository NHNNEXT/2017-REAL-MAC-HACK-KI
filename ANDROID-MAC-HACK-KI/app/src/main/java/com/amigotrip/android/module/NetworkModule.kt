package com.amigotrip.android.module

import android.content.Context
import com.amigotrip.android.cookie.PersistentCookieStore
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.JavaNetCookieJar
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Zimincom on 2017. 12. 13..
 */
@Module(includes = arrayOf(ContextModule::class))
class NetworkModule {

    private val baseUrl = if (BuildConfig.DEBUG) {
        "http://www.amigotrip.co.kr"
    } else {
        "http://www.amigotrip.co.kr"
    }



    @Provides
    @Singleton
    fun provideCookieStore(context: Context): PersistentCookieStore {
        return PersistentCookieStore(context)
    }

    @Provides
    @Singleton
    fun provideCookieManager(cookieStore: PersistentCookieStore): CookieManager {
        return CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gson = GsonBuilder().setLenient().create()
        return gson
    }

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideCookieJar(cookieManager: CookieManager): JavaNetCookieJar {
        return JavaNetCookieJar(cookieManager)
    }


    @Provides
    @Singleton
    fun provideOKHttpClient(interceptor: HttpLoggingInterceptor,
                            cookieJar: JavaNetCookieJar ) : OkHttpClient {

        val builder = OkHttpClient.Builder()

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): AmigoService{

        return retrofit.create(AmigoService::class.java)
    }
}