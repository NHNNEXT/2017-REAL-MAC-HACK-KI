package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amigotrip.android.AmigoApplication
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.isEmpty
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_email_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.regex.Pattern

class EmailSignUpActivity : AppCompatActivity() {

    lateinit var amigoService: AmigoService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_up)

        amigoService = AmigoApplication.amigoService

        btn_sign_in.setOnClickListener {

            if (!checkInput()) {
                Toast.makeText(this, "please check input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = input_nickname.string
            val email = input_email.string
            val password = input_pw.string

            val user = User(name = name, email = email, password = password, id = null,
                    profileImg = null)

            requestNewUser(user)

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

        progress_sign_up.visibility = View.VISIBLE

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>) {

                if (!response.isSuccessful) return

                if (response.code() == 400) {
                    Toast.makeText(this@EmailSignUpActivity,
                            "check your email", Toast.LENGTH_SHORT).show()
                } else {
                    Timber.d(user.toString())
                    addFirebaseInfo(user)
                    requestLogin(user)
                }

                progress_sign_up.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Timber.d("sign up fail")
            }
        })

    }

    private fun addFirebaseInfo(user: User) {
        val userRef = FirebaseDatabase.getInstance().getReference("users")

        val userTempKey = userRef.push().key

        val token = FirebaseInstanceId.getInstance().token
        //plus device id (for FCM)
        userRef.child(userTempKey).child("email").setValue(user.email)
        userRef.child(userTempKey).child("deviceKey").setValue(token)


    }

    private fun requestLogin(user: User) {

        progress_sign_up.visibility = View.VISIBLE

        val call = amigoService.loginUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>) {

                if (!response.isSuccessful) return


                if (response.code() == 400) {
                    Toast.makeText(this@EmailSignUpActivity,
                            "check your input", Toast.LENGTH_SHORT).show()
                } else {

                    val user = response.body()
                    Timber.d(user.toString())
                    UserInfoManager.setUserInfo(user)

                    val intent = Intent(this@EmailSignUpActivity,
                            MainActivity::class.java)
                    startActivity(intent)
                }

                progress_sign_up.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.w("requset login", "failed")
            }

        })


    }

    private fun checkInput(): Boolean {

        var result = true

        if (input_email.isEmpty()) {
            input_email.error = "check email!"
            result = false
        }

        if (input_pw.isEmpty()) {
            input_pw.error = "check password"
            result = false
        }

        if (!isEmailValid(input_email.string)) {
            input_email.error = "please check email"
            result = false
        }

        if (input_pw_confirm.isEmpty()) {
            input_pw_confirm.error = "please check confirm"
            result = false
        }

        if (input_pw_confirm.text == input_pw.text) {
            input_pw_confirm.error = "password does not match"
            result = false
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
