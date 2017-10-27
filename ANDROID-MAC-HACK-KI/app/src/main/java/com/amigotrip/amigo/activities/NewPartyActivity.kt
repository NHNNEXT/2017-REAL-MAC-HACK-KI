package com.amigotrip.amigo.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.amigotrip.amigo.R
import com.amigotrip.amigo.datas.Party
import com.amigotrip.amigo.remote.AmigoService
import kotlinx.android.synthetic.main.activity_new_party.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class NewPartyActivity : AppCompatActivity() {

    var gender = "male"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_party)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Amigo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val amigoService = AmigoService.getService(AmigoService::class.java)



        radio_group.setOnCheckedChangeListener { radiogrop, id ->
            run {
                when (id) {
                    R.id.radio_male -> gender = "male"
                    R.id.radio_female -> gender = "female"
                }
            }
        }




        btn_submit.setOnClickListener {

            val name = input_name.text.toString()
            //val age = input_age.text.toString().toInt()

            val email = input_email.text.toString()

            if (isEmailValid(email)) {
                Toast.makeText(this, "apply done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(MainActivity@ this, NewPartyActivity::class.java))
            } else {
                input_email.error = "wrong email!"
            }

            val language = input_theme.text.toString()
            val theme = input_attraction.text.toString()
            val attraction = input_attraction.text.toString()

            val party =
                    Party(name, "wlals822@naver.com", 20, gender, language, "12/24", theme,
                            attraction)
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

            startActivity(Intent(NewPartyActivity@ this, DoneActivity::class.java))
        }
    }

    fun isEmailValid(email: String): Boolean {

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
