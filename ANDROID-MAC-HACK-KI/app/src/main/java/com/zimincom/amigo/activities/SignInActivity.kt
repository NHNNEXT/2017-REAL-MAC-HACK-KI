package com.zimincom.amigo.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.zimincom.amigo.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_view.setOnClickListener(this)
        btn_apply.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        val uri = Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.intro)
        videoView.setVideoURI(uri)
        videoView.start()
    }

    fun isEmailValid(email: String): Boolean {

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_view -> {
                val email = input_email.text.toString()

                if (isEmailValid(email)) {
                    Toast.makeText(this, "apply done", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(MainActivity@this, PartiesActivity::class.java))
                } else {
                    input_email.error = "wrong email!"
                }
            }
            btn_apply -> {
                val email = input_email.text.toString()

                if (isEmailValid(email)) {
                    Toast.makeText(this, "apply done", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(MainActivity@this, NewPartyActivity::class.java))
                } else {
                    input_email.error = "wrong email!"
                }
            }
        }
    }
}
