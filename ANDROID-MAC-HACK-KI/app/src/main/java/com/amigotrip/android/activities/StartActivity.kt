package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amigotrip.anroid.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity : AppCompatActivity() {

    private val RC_GOOGLE_SIGN_IN: Int = 20

    lateinit var googleSigninClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.KEY_GOOGLE_SERVER_CLIENT))
                .requestEmail()
                .build()

        googleSigninClient = GoogleSignIn.getClient(this, gso)

        iv_start_email.setOnClickListener { signEmail() }
        iv_start_fb.setOnClickListener { signFB() }
        iv_start_google.setOnClickListener { signGoogle() }
        tv_tour.setOnClickListener { showHome() }
        tv_sign_in.setOnClickListener{ showSignIn() }
    }

    private fun showSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    private fun showHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun signEmail() {
        val intent = Intent(this, EmailSignUpActivity::class.java)
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
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val name = account.displayName
            val email = account.email
            val tempPassword = account.idToken

            Log.d("start", name + email + tempPassword)

        } catch (e: ApiException) {
            Log.d("start", "signInResult:failed code=" + e.statusCode)
        }
    }
}
