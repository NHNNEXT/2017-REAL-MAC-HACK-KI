package com.zimincom.amigo.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zimincom.amigo.R
import kotlinx.android.synthetic.main.fragment_intro.*

/**
 * Created by Zimincom on 2017. 10. 22..
 */
class IntroPage1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.intro)

        video_intro.setVideoURI(uri)
        video_intro.isSoundEffectsEnabled = false
        video_intro.start()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

    }
}