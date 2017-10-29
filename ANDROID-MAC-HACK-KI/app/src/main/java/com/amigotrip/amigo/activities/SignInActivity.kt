package com.amigotrip.amigo.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.amigotrip.amigo.R
import com.amigotrip.amigo.adpaters.IntroPagerAdapter
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_view.setOnClickListener(this)
        btn_apply.setOnClickListener(this)

        pager_intro.adapter = (IntroPagerAdapter(supportFragmentManager))
        pager_intro.offscreenPageLimit = 0
    }


    override fun onClick(view: View?) {
        when (view) {
            btn_view -> {

                Toast.makeText(this, "apply done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(MainActivity@ this, PartiesActivity::class.java))

            }

            btn_apply -> {


                val intent = Intent(SignInActivity@ this, NewPartyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
