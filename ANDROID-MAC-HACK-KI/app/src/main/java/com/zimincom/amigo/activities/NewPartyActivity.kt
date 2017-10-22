package com.zimincom.amigo.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zimincom.amigo.R
import com.zimincom.amigo.datas.Party
import com.zimincom.amigo.remote.AmigoService
import kotlinx.android.synthetic.main.activity_new_party.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPartyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_party)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Amigo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val amigoService = AmigoService.getService(AmigoService::class.java)

        val party =
                Party("zimin", "aaa@naver.com", 24, "female", "korea", "12/24", "seoul", "lotte")

        val call = amigoService.newParty(party)

        call.enqueue(object : Callback<Party> {
            override fun onFailure(call: Call<Party>?, t: Throwable?) {
                Log.d("retrofit", "onfail")
                Log.d("new party", t.toString())
            }

            override fun onResponse(call: Call<Party>?, response: Response<Party>?) {

                if (response!!.isSuccessful) {
                    Log.d("retrofit", "on response success")
                }
            }
        })

        btn_submit.setOnClickListener {
            startActivity(Intent(NewPartyActivity@ this, DoneActivity::class.java))
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
