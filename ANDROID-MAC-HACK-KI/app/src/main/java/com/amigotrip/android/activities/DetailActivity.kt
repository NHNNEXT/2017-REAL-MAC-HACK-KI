package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.anroid.R
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        email = intent.getStringExtra("email")
        Timber.d(email)

    }

}
