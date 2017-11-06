package com.amigotrip.android.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.amigotrip.anroid.R
import java.util.*

class SplashActivity : Activity() {

    val preferences: SharedPreferences by lazy {
        getSharedPreferences(getString(R.string.KEY_PREFERENCE), Context.MODE_PRIVATE)
    }

    lateinit var timerTask: TimerTask
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        timerTask = object : TimerTask() {
            override fun run() {
                delegateActivity()
            }
        }

        timer = Timer()
        timer.schedule(timerTask, 500)

    }

    private fun delegateActivity() {

        if (isLogin()) {
            val intent = Intent(SplashActivity@this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(SplashActivity@this, IntroActivity::class.java)
            startActivity(intent)
        }

    }
    private fun isLogin(): Boolean {
        return preferences.getBoolean(getString(R.string.KEY_ISSIGNIN), false)
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
