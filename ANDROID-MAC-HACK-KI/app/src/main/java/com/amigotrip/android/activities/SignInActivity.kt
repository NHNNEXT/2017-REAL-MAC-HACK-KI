package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.anroid.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_signin.*

class SignInActivity : AppCompatActivity() {


    private val RC_GOOGLE_SIGN_IN: Int = 20

    lateinit var googleSigninClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        googleSigninClient = GoogleSignIn.getClient(this, gso)

        btn_signin_email.setOnClickListener { signEmail() }
        btn_signin_fb.setOnClickListener { signFB() }
        btn_signin_google.setOnClickListener { signGoogle() }
    }

    private fun signEmail() {
        val intent = Intent(this, EmailSignInActivity::class.java)
        startActivity(intent)
    }

    private fun signFB() {

    }


    private fun signGoogle() {
        val intent = googleSigninClient.signInIntent
        startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {

        }
    }

}
