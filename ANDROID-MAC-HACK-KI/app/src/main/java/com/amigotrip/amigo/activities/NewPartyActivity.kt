package com.amigotrip.amigo.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.Toast
import com.amigotrip.amigo.NumberPickerDialog
import com.amigotrip.amigo.R
import com.amigotrip.amigo.datas.Party
import com.amigotrip.amigo.remote.AmigoService
import kotlinx.android.synthetic.main.activity_new_party.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class NewPartyActivity : AppCompatActivity(),
        View.OnClickListener,
        NumberPicker.OnValueChangeListener,
        DatePickerDialog.OnDateSetListener {

    //refactor
    var gender = "male"

    companion object {
        const val REQUEST_LANGUAGE: Int = 101
    }

    val amigoService: AmigoService by lazy {
        AmigoService.getService(AmigoService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_party)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Amigo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        radio_group.setOnCheckedChangeListener { radiogrop, id ->
            run {
                when (id) {
                    R.id.radio_male -> gender = "male"
                    R.id.radio_female -> gender = "female"
                }
            }
        }

        btn_submit.setOnClickListener(this)
        tv_select_age.setOnClickListener(this)
        tv_choose_date.setOnClickListener(this)
        tv_choose_lang.setOnClickListener(this)

    }

    fun isEmailValid(email: String): Boolean {

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }


    override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
        tv_select_age.text = p1.toString()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        tv_choose_date.text = "date:" + p1 + "." + p2 + "." + p3
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_submit -> {
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
            tv_select_age -> {
                val numberPickerDialog = NumberPickerDialog()
                numberPickerDialog.setValueChangeListener(this)
                numberPickerDialog.show(supportFragmentManager, "time picker");
            }
            tv_choose_date -> {
                val datePickerDialog =
                        DatePickerDialog(NewPartyActivity@ this, this, 2017, 11, 0)
                datePickerDialog.show()
            }
            tv_choose_lang -> {
                val intent = Intent(NewPartyActivity@ this, ChooseLangActivity::class.java)
                startActivityForResult(intent, REQUEST_LANGUAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_LANGUAGE) {
                tv_lang_list.append(data?.getStringArrayListExtra("langs").toString())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
