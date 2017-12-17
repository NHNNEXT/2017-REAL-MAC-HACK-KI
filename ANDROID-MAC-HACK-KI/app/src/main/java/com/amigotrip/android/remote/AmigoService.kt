package com.amigotrip.android.remote

import com.amigotrip.android.datas.ApiResult
import com.amigotrip.android.datas.Article
import com.amigotrip.android.datas.PhotoResult
import com.amigotrip.android.datas.User
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Zimincom on 2017. 10. 19..
 */
interface AmigoService {

    //users api
    @POST("/users")
    fun addUser(@Body user: User): Observable<User>

    @POST("/users/login")
    fun loginUser(@Body user: User): Observable<User>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Call<String>


    //articles api
    @GET("/articles/locals")
    fun getArticles(): Observable<List<Article>>

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

}