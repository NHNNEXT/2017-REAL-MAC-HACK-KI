package com.amigotrip.android.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.ApiResult
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.isEmpty
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_email_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class EmailSignUpActivity : AppCompatActivity() {

    val amigoService = AmigoService.getService(AmigoService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_in)


        btn_sign_in.setOnClickListener {

            if (!checkInput()) {
                Toast.makeText(this, "please check input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = input_nickname.string
            val email = input_email.string
            val password = input_pw.string

            val user = User(name = name, email = email, password = password)

            requestNewUser(user)
            UserInfoManager.setUserInfo(user)

            startActivity(Intent(this@EmailSignUpActivity, MainActivity::class.java))
        }
    }


    /**
     * Make sign up request to server
     *
     * @param user User info which wants to create
     *
     * @author zimin
     */
    private fun requestNewUser(user: User) {

        val call = amigoService.addUser(user)

        //login feature checked
        call.enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult>?, response: Response<ApiResult>) {
                if (response.isSuccessful) {

                    val responseString = response.body()!!.url

                    val userId = getUserIdFrom(responseString)

                    requestLogin(user)

                } else {
                    Log.w("sigin", response.code().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult>?, t: Throwable?) {
                Log.d("email sign in", "error")
            }
        })
    }

    private fun requestLogin(user: User) {

        val call = amigoService.loginUser(user)

        call.enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult>?, response: Response<ApiResult>) {
                if (response.isSuccessful) {

                    val preferences =
                            getSharedPreferences(getString(R.string.KEY_PREFERENCE), Context.MODE_PRIVATE)
                    //회원가입이 된 상태로 다른 액티비티에서 로그인 시에 이것이 가능하지 않음

                    val editor = preferences.edit()
                    editor.putBoolean(getString(R.string.KEY_ISSIGNIN), true)
                    editor.putInt(getString(R.string.KEY_USER_ID), user.id)
                    editor.putString(getString(R.string.KEY_USER_NAME), user.name)
                    editor.putString(getString(R.string.KEY_USER_EMAIL), user.email)
                    editor.apply()

                    val intent = Intent(this@EmailSignUpActivity,
                                    MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("request login", response.code().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult>?, t: Throwable?) {
               Log.w("requset login", "failed")
            }

        })


    }

    private fun checkInput(): Boolean {

        var result = false

        if (input_email.isEmpty()) {
            input_email.error = "check email!"
        } else if (input_pw.isEmpty()) {
            input_pw.error = "check password"
        } else if (!isEmailValid(input_email.string)) {
            input_email.error = "please check email"
        } else if (input_pw_confirm.isEmpty()) {
            input_pw_confirm.error = "please check confirm"
        } else if (input_pw_confirm.text == input_pw.text) {
            input_pw_confirm.error = "password does not match"
        } else {
            result = true
        }

        return result

    }


    private fun getUserIdFrom(url: String): Int {
        val pattern = Pattern.compile("\\d+")
        val matcher = pattern.matcher(url)

        return if (matcher.find()) matcher.group(0).toInt() else 0
    }

    private fun isEmailValid(email: String): Boolean {

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }
}
