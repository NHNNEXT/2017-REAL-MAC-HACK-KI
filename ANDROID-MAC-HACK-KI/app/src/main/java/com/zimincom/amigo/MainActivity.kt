package com.zimincom.amigo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        btn_apply.setOnClickListener {
            val email = input_email.text.toString()

            if (isEmailValid(email)) {
                Toast.makeText(this, "apply done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(MainActivity@this, NewPartyActivity::class.java))
            } else {
               input_email.error = "wrong email!"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro)
        videoView.setVideoURI(uri)
        videoView.start()
    }
    fun isEmailValid(email: String): Boolean {

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }
}
