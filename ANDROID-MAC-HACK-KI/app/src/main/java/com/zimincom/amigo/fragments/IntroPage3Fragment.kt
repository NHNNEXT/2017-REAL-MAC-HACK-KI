package com.zimincom.amigo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zimincom.amigo.R

/**
 * Created by Zimincom on 2017. 10. 22..
 */
class IntroPage3Fragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_intro, container, false)
    }
}