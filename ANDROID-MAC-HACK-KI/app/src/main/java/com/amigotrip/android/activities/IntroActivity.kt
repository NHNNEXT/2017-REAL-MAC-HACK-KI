package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amigotrip.android.adpaters.IntroPagerAdapter
import com.amigotrip.android.fragments.IntroPage1Fragment
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity(), View.OnClickListener {

    val pagerAdapter: IntroPagerAdapter by lazy {
        IntroPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btn_sign_in.setOnClickListener(this)
        btn_sign_up.setOnClickListener(this)

        addIntroPage(R.drawable.korean_travel, R.string.message_intro1)
        addIntroPage(R.drawable.travel_mate, R.string.message_intro2)

        pager_intro.adapter = pagerAdapter
        pager_intro.offscreenPageLimit = 0
    }

    fun addIntroPage(imageId: Int, messageId: Int) {

        val introPage = IntroPage1Fragment()
        val bundle = Bundle()
        bundle.putInt("imageId", imageId)
        bundle.putInt("messageId", messageId)
        introPage.arguments = bundle

        pagerAdapter.addFragment(introPage)

    }

    override fun onClick(view: View?) {
        when (view) {
            btn_sign_in -> {

                startActivity(Intent(IntroActivity@ this, MainActivity::class.java))
            }

            btn_sign_up -> {
                val intent = Intent(IntroActivity@ this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
