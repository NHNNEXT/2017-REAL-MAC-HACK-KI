package com.zimincom.amigo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_parties.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parties)

        val amigoService = AmigoService.getService(AmigoService::class.java)

        val call = amigoService.showParties()

        call.enqueue(object: Callback<List<Party>>{
            override fun onResponse(call: Call<List<Party>>?, response: Response<List<Party>>?) {
                if (response!!.isSuccessful) {
                    Log.d("parties", "success")
                    tv_result.text = response.body().toString()
                }
            }

            override fun onFailure(call: Call<List<Party>>?, t: Throwable?) {
                Log.d("parties", "failed" + t.toString())
            }
        })
    }
}
