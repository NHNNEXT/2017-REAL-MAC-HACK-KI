package com.amigotrip.android.datas

/**
 * Created by Zimincom on 2017. 11. 11..
 */
data class User(
        var name: String,
        var email: String,
        var gender: String = "",
        var age: Int= 0,
        var nationality: String = "",
        var city: String = "",
        var creditPoint: Int = 0,
        var profileImg: String = "",
        var contents: String = ""
)