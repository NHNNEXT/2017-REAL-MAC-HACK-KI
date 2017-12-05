package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.utils.ChatUtil
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
  
    private lateinit var targetEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        targetEmail = intent.getStringExtra("email")
        Timber.d(targetEmail)

        val chatLib = ChatUtil(this)

        iv_chat.setOnClickListener {
            chatLib.startChatWith(targetEmail)
        }
    }
}
