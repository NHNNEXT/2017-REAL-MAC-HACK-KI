package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R

class PartiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parties)

        val amigoService = AmigoService.getService(AmigoService::class.java)


    }
}
