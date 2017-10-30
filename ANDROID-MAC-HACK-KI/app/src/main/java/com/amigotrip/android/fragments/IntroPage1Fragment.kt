package com.amigotrip.android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.anroid.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_intro_1.*

/**
 * Created by Zimincom on 2017. 10. 22..
 */
class IntroPage1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_intro_1, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val bundle = arguments
        val imageId = bundle.getInt("imageId")
        val messageId = bundle.getInt("messageId")

        Picasso.with(context).load(imageId).into(iv_intro)
        tv_message.text = resources.getString(messageId)
    }

}