package com.amigotrip.android.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.Toast
import com.amigotrip.android.datas.Party
import com.amigotrip.android.dialogs.NumberPickerDialog
import com.amigotrip.android.extentions.isEmpty
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_new_party.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern


class NewPartyActivity : AppCompatActivity(),
        View.OnClickListener,
        NumberPicker.OnValueChangeListener,
        DatePickerDialog.OnDateSetListener {

    var languageList = arrayListOf<String>()

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

        setListeners()
    }

    private fun setListeners() {

        btn_submit.setOnClickListener(this)
        tv_select_age.setOnClickListener(this)
        tv_choose_date.setOnClickListener(this)
        tv_choose_lang.setOnClickListener(this)
    }

    override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
        tv_age.text = p1.toString()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        tv_selected_date.text = String.format("%d.%d.%d", p1, p2, p3)
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_submit -> {
                if (checkInputs()) {
                    submitInfo()
                }
            }

            tv_select_age -> {
                val numberPickerDialog = NumberPickerDialog()
                numberPickerDialog.setValueChangeListener(this)
                numberPickerDialog.show(supportFragmentManager, "time picker");
            }

            tv_choose_date -> {

                val now = Calendar.getInstance()
                val datePickerDialog =
                        DatePickerDialog(NewPartyActivity@ this,
                                this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH))

                datePickerDialog.show()
            }

            tv_choose_lang -> {
                val intent = Intent( NewPartyActivity@this, ChooseLangActivity::class.java)
                startActivityForResult(intent, REQUEST_LANGUAGE)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_LANGUAGE) {
                val list = data?.getStringArrayListExtra("langs") as ArrayList<String>
                languageList.addAll(list)
                tv_lang_list.append(languageList.toString())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //hide method
    private fun checkInputs(): Boolean {

        var result = true

        if (input_name.isEmpty()) {
            input_name.error = "check name!"
            result = false
        }

        if (!isEmailValid(input_email.string)) {
            input_email.error = "please check email"
            result = false
        }

        //used text util
        if (TextUtils.isEmpty(tv_age.text)){
            Toast.makeText(baseContext, "please choose age", Toast.LENGTH_SHORT).show()
            result = false
        }

        if (tv_selected_date.isEmpty()) {
            Toast.makeText(baseContext, "please choose date", Toast.LENGTH_SHORT).show()
            result = false
        }

        return result

    }

    private fun submitInfo() {

        val name = input_name.string
        val age = tv_age.string.toInt()
        val email = input_email.string

        val gender = if (radio_group.checkedRadioButtonId == R.id.radio_male) "male" else "female"
        val date = tv_selected_date.string

        val theme = input_attraction.string
        val attraction = input_attraction.string

        val party = Party(name, email, age, gender,
                        languageList.toString(),
                        date,
                        theme,
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

        startActivity(Intent(NewPartyActivity@ this, WelcomeActivity::class.java))
    }


    private fun isEmailValid(email: String): Boolean {

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }
}
