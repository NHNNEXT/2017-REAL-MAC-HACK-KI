package com.amigotrip.android

import android.content.Context
import android.content.SharedPreferences
import com.amigotrip.android.datas.User
import com.amigotrip.anroid.R

/**
 * Created by Zimincom on 2017. 11. 16..
 */
object UserInfoManager {

    private lateinit var preference: SharedPreferences
    private lateinit var user: User

    fun initManager(context: Context) {
        preference = context.getSharedPreferences(context.getString(R.string.KEY_PREFERENCE),
                Context.MODE_PRIVATE)
    }

    fun getPreference() = preference

    fun isUserLogin() : Boolean {
        return preference.getBoolean(AppKeys.isLogin, false)
    }

    fun removeUser() {
        preference.edit().clear().apply()
    }

    fun getLogineduser() {

    }


}