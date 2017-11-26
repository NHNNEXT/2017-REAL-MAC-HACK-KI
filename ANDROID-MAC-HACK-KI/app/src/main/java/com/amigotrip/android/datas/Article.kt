package com.amigotrip.android.datas

/**
 * Created by Zimincom on 2017. 11. 26..
 */
data class Article (
        var beginDate: String,
        var contents: String,
        var createDate: String,
        var id: Int,
        var location: String,
        var photos: List<Photo>,
        var writer: User
) {
}