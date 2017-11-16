package com.amigotrip.android.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amigotrip.android.datas.ApiResult
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_signin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {


    private val RC_GOOGLE_SIGN_IN: Int = 20

    lateinit var googleSigninClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        googleSigninClient = GoogleSignIn.getClient(this, gso)

        btn_sign_in.setOnClickListener { signInByInput() }
        btn_signin_email.setOnClickListener { signEmail() }
        btn_signin_fb.setOnClickListener { signFB() }
        btn_signin_google.setOnClickListener { signGoogle() }
    }

    private fun signInByInput() {
        val email = input_email.string
        val pw = input_pw.string

        val user = User(email = email, password = pw)
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

                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                Log.d("signin", response.toString())
            }

            override fun onFailure(call: Call<ApiResult>?, t: Throwable?) {
                Log.d("signin", t.toString())
            }
        })
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
