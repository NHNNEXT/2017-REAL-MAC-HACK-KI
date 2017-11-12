package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_signin.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btn_signin_email.setOnClickListener { signEmail() }
        btn_signin_fb.setOnClickListener { signFB() }
        btn_signin_google.setOnClickListener { signGoogle() }
    }

    private fun signEmail() {

    }

    private fun signFB() {

    }

    private fun signGoogle() {

    }
}
