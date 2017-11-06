package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amigotrip.android.adpaters.IntroPagerAdapter
import com.amigotrip.android.fragments.IntroPage1Fragment
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    val pagerAdapter: IntroPagerAdapter by lazy {
        IntroPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_view.setOnClickListener(this)
        btn_apply.setOnClickListener(this)

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
            btn_view -> {
                startActivity(Intent(SignInActivity@this, WebViewActivity::class.java))
            }

            btn_apply -> {
                val intent = Intent(SignInActivity@ this, NewPartyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
