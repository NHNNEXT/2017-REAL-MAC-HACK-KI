package com.amigotrip.android.remote

import com.amigotrip.android.datas.Party
import com.amigotrip.android.datas.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Zimincom on 2017. 10. 19..
 */
interface AmigoService {

    @POST("/party/guest")
    fun newParty(@Body party: Party): Call<Party>

    @POST("/user")
    fun addUser(@Body user: User): Call<User>

    @GET("/party/guest")
    fun showParties(): Call<List<Party>>


    companion object {

        val baseUrl = "http://www.amigotrip.co.kr"

        fun getService(java: Class<AmigoService>): AmigoService {

            val retrofit =
                    Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(createOkHttpClient())
                            .build()

            return retrofit.create(java)
        }

        private fun createOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)

            return builder.build()
        }

    }

}