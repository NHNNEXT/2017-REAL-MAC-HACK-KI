package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_email_sign_in.*

class SignInActivity : AppCompatActivity() {

    val amigoService = AmigoService.getService(AmigoService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_sign_in.setOnClickListener { signInUser() }
    }


    private fun signInUser() {

        val email = input_email.string
        val password = input_email.string

        val user = User(email = email, password = password)

        UserInfoManager.setUserInfo(user)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

//        val call = amigoService.loginUser(user)

//        call.enqueue(object : Callback<ApiResult> {
//            override fun onResponse(call: Call<ApiResult>?, response: Response<ApiResult>) {
//                if (response.isSuccessful) {
//
//                    val preferences =
//                            getSharedPreferences(getString(R.string.KEY_PREFERENCE), Context.MODE_PRIVATE)
//                    //회원가입이 된 상태로 다른 액티비티에서 로그인 시에 이것이 가능하지 않음
//
//                    val editor = preferences.edit()
//                    editor.putBoolean(getString(R.string.KEY_ISSIGNIN), true)
//                    editor.putInt(getString(R.string.KEY_USER_ID), user.id)
//                    editor.putString(getString(R.string.KEY_USER_NAME), user.name)
//                    editor.putString(getString(R.string.KEY_USER_EMAIL), user.email)
//                    editor.apply()
//
//                    val intent = Intent(this@SignInActivity,
//                            MainActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Log.d("request login", response.code().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<ApiResult>?, t: Throwable?) {
//                Log.w("requset login", "failed")
//            }
//
//        })


    }
}
