package com.amigotrip.android.datas

/**
 * Created by Zimincom on 2017. 11. 11..
 */
data class User(
        var id: Int? = null,
        var name: String = "",
        var email: String = "",
        var gender: String = "",
        var age: Int= 0,
        var nationality: String = "",
        var city: String = "",
        var profileImg: String? = null,
        var contents: String = "",
        var password: String = ""
)