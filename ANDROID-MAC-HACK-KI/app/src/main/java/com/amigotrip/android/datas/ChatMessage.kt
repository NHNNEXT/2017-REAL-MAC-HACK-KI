package com.amigotrip.android.datas

/**
 * Created by Zimincom on 2017. 11. 25..
 */
data class ChatMessage(
        var type: Int = 11,
        var message: String = "",
        var email: String = "",
        var userName: String = ""){
}