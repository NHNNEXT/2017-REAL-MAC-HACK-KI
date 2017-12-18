package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.amigotrip.android.AmigoApplication
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.User
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    lateinit var amigoService: AmigoService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        amigoService = (application as AmigoApplication).component.service()

        btn_sign_in.setOnClickListener { signInUser() }
    }


    private fun signInUser() {

        progress_sign_in.visibility = View.VISIBLE

        val email = input_email.string
        val password = input_password.string
        val user = User(email = email, password = password)

        // todo 기존 enqueue 방식에서 onFailure 처리는 ?

        val signInObserver =
                object : DisposableObserver<User>() {
                    override fun onNext(responseUser: User) {
                        responseUser.password = user.password
                        UserInfoManager.setUserInfo(responseUser)

                        val userRef = FirebaseDatabase.getInstance().getReference("users")
                        val query = userRef.orderByChild("email").equalTo(user.email)

                        query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(err: DatabaseError?) {
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                snapshot?.children?.forEach {
                                    UserInfoManager.setKey(snapshot.key)
                                }
                            }
                        })
                    }

                    override fun onComplete() {
                        // 잘 끝났을 시 호출 , 다만 여기는 응답이 하나이기에 별 차이는 없다 . 인자가 없음
                        val intent = Intent(this@SignInActivity,
                                MainActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(this@SignInActivity,
                                "check your input! no account", Toast.LENGTH_SHORT).show()
                    }

                }

        //아마도 여기서 오는 onError 에서 기존 onFailure 가 처리될듯.
        amigoService.loginUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { progress_sign_in.visibility = View.INVISIBLE }
                .subscribeWith(signInObserver)

    }
}
