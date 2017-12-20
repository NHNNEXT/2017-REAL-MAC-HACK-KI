package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.amigotrip.android.utils.ChatUtil
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
  
    private lateinit var targetEmail: String
    private var articleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        targetEmail = intent.getStringExtra("email")
        articleId = intent.getIntExtra("articleId", 0)

        Timber.d(targetEmail)

        val chatLib = ChatUtil(this)

        iv_chat.setOnClickListener {
            chatLib.startChatWith(targetEmail)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId

        when (id) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
