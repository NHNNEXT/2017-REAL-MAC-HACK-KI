package com.amigotrip.android.datas

/**
 * Created by Zimincom on 2017. 11. 11..
 */
data class User(
        var id: Int = 0,
        var name: String = "",
        var email: String = "",
        var gender: String = "",
        var age: Int= 0,
        var nationality: String = "",
        var city: String = "",
        var profileImg: String = "",
        var contents: String = "",
        val password: String = ""
)