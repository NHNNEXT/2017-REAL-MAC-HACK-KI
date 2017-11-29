package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    val amigoService = AmigoService.getService(AmigoService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_sign_in.setOnClickListener { signInUser() }
    }


    private fun signInUser() {

        val email = input_email.string
        val password = input_password.string

        val user = User(email = email, password = password)

        val call = amigoService.loginUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>) {
                if (response.isSuccessful) {


                    UserInfoManager.setUserInfo(response.body())

                    val intent = Intent(this@SignInActivity,
                            MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("request login", response.code().toString())
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.w("requset login", "failed")
            }

        })


    }
}
