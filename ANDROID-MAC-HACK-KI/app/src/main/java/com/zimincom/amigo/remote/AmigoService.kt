package com.zimincom.amigo.remote

import com.zimincom.amigo.datas.Party
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

    @GET("/party/guest")
    fun showParties(): Call<List<Party>>


    companion object {
        fun getService(java: Class<AmigoService>): AmigoService {

            val retrofit =
                    Retrofit.Builder()
                            .baseUrl("http://211.249.60.54")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(createOkHttpClient())
                            .build()

            return retrofit.create(java)
        }

        fun createOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            val intercepter = HttpLoggingInterceptor()

            intercepter.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(intercepter)

            return builder.build()
        }

    }

}