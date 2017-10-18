package com.zimincom.amigo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_party.*

class NewPartyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_party)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Amigo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_submit.setOnClickListener {
            startActivity(Intent(NewPartyActivity@this, DoneActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
