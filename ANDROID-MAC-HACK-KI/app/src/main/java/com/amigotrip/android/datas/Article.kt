package com.amigotrip.android.datas

/**
 * Created by Zimincom on 2017. 11. 26..
 */
data class Article (
        var contents: String,
        var createDate: String?,
        var id: Int?,
        var location: String,
        var photos: List<PhotoResult>?,
        var writer: User?
)