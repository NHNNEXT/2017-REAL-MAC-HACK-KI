package com.amigotrip.amigo.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.amigo.R
import kotlinx.android.synthetic.main.activity_done.*

class DoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "congraturation!"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
