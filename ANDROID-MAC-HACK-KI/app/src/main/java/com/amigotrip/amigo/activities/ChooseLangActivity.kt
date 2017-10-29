package com.amigotrip.amigo.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.amigotrip.amigo.R
import kotlinx.android.synthetic.main.activity_choose_lang.*
import java.io.BufferedReader
import java.io.InputStreamReader


class ChooseLangActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lang)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var langList = arrayListOf<String>()
        val selectedLangs = arrayListOf<String>()

        val inputStream = assets.open("langs.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))

        var line = reader.readLine()

        while (line != null) {
            langList.add(line)
            line = reader.readLine()
        }

        autoCompleteTextView.setAdapter(object : ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, langList) {})

        btn_add.setOnClickListener {
            selectedLangs.add(autoCompleteTextView.text.toString())
            addLang(autoCompleteTextView.text.toString())
            autoCompleteTextView.setText("")
        }

        btn_apply.setOnClickListener {
            val intent = Intent()
            intent.putStringArrayListExtra("langs", selectedLangs)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun addLang(lang: String) {
        tv_selected_list.append(lang + "\n")
    }
}
