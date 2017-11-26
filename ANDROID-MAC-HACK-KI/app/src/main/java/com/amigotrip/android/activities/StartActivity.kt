package com.amigotrip.android.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amigotrip.android.datas.ApiResult
import com.amigotrip.android.datas.User
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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


    private fun signInBy(name: String, email: String, password: String) {

        val user = User(name = name, email = email, password = password)
        val amigoService = AmigoService.getService(AmigoService::class.java)
        val call = amigoService.loginUser(user)

        call.enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult>?, response: Response<ApiResult>) {
                if (response.isSuccessful) {

                    val preference = getSharedPreferences(getString(R.string.KEY_PREFERENCE),
                            Context.MODE_PRIVATE)
                    val editor = preference.edit()
                    editor.putBoolean(getString(R.string.KEY_ISSIGNIN), true)
                    editor.putInt(getString(R.string.KEY_USER_ID), user.id)
                    editor.putString(getString(R.string.KEY_USER_NAME), user.name)
                    editor.putString(getString(R.string.KEY_USER_EMAIL), user.email)
                    editor.apply()

                    val intent = Intent(this@StartActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                Log.d("signin", response.toString())
            }

            override fun onFailure(call: Call<ApiResult>?, t: Throwable?) {
                Log.d("signin", t.toString())
            }
        })
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
