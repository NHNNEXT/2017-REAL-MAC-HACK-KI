package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_sign_in.setOnClickListener { signInUser() }
    }


    private fun signInUser() {

        progress_sign_in.visibility = View.VISIBLE

        val email = input_email.string
        val password = input_password.string

        val user = User(email = email, password = password)

        val amigoService = AmigoService.getService(AmigoService::class.java, this)
        val call = amigoService.loginUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>) {
                if (response.isSuccessful) {

                    UserInfoManager.setUserInfo(response.body())
                    val intent = Intent(this@SignInActivity,
                            MainActivity::class.java)

                    val userRef = FirebaseDatabase.getInstance().getReference("users")
                    val query = userRef.orderByChild("email").equalTo(email)

                    query.addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(err: DatabaseError?) {
                        }

                        override fun onDataChange(snapshot: DataSnapshot?) {
                            snapshot?.children?.forEach {
                                snapshot -> UserInfoManager.setKey(snapshot.key)
                            }
                        }
                    })

                    startActivity(intent)

                } else {
                    //when 400
                    Toast.makeText(this@SignInActivity,
                            "check your input! no account", Toast.LENGTH_SHORT).show()
                }

                progress_sign_in.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.w("requset login", "failed")
            }

        })

    }
}
