package com.amigotrip.android.remote

import android.content.Context
import com.amigotrip.android.cookie.PersistentCookieStore
import com.amigotrip.android.datas.*
import com.amigotrip.anroid.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.internal.JavaNetCookieJar
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/**
 * Created by Zimincom on 2017. 10. 19..
 */
interface AmigoService {

    //test api
    @POST("/party/guest")
    fun newParty(@Body party: Party): Call<Party>

    //users api
    @POST("/users")
    fun addUser(@Body user: User): Call<User>

    @POST("/users/login")
    fun loginUser(@Body user: User): Call<User>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Call<String>


    //articles api
    @GET("/articles/locals")
    fun getArticles(): Call<List<Article>>

    @POST("/articles/locals")
    fun postArticle(@Body article: Article): Call<Article>

    @PUT("/articles/locals")
    fun putAriticle(): Call<ApiResult>


    //image upload api
    @Multipart
    @POST("/photos/{articleId}")
    fun uploadPhoto(
            @Part("description") description: RequestBody,
            @Part file: MultipartBody.Part,
            @Path("articleId") id : Int
    ): Call<PhotoResult>


    companion object {

        private val baseUrl =  if (BuildConfig.DEBUG) {
            "http://dev.amigotrip.co.kr"
        } else {
            "http://www.amigotrip.co.kr"
        }



        fun getService(java: Class<AmigoService>, context: Context): AmigoService {

            //to handle plain string(not Json) , Add setLenient()
            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val retrofit =
                    Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(createOkHttpClient(context))
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()

            return retrofit.create(java)
        }

        private fun createOkHttpClient(context: Context): OkHttpClient {
            val builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            val cookieStore = PersistentCookieStore(context)
            val cookieManager = CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL)
            val cookieJar = JavaNetCookieJar(cookieManager)

            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
                    .cookieJar(cookieJar)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)

            return builder.build()
        }

    }

}