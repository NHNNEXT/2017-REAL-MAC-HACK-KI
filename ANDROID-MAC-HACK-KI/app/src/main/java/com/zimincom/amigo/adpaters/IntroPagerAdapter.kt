package com.zimincom.amigo.adpaters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.zimincom.amigo.fragments.IntroPage1Fragment
import com.zimincom.amigo.fragments.IntroPage2Fragment

/**
 * Created by Zimincom on 2017. 10. 22..
 */
class IntroPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val introPageList = listOf(IntroPage1Fragment(), IntroPage2Fragment())

    override fun getItem(position: Int): Fragment {
        return introPageList[position]
    }

    override fun getCount(): Int {
        return introPageList.size
    }
}