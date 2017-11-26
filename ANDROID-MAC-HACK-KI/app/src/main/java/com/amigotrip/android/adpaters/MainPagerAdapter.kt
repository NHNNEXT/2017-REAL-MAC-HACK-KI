package com.amigotrip.android.adpaters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.amigotrip.android.fragments.ChatRoomsFragment
import com.amigotrip.android.fragments.LocalListFragment
import com.amigotrip.android.fragments.MoreInfoFragment
import com.amigotrip.android.fragments.TravelerListFragment

/**
 * Created by Zimincom on 2017. 11. 6..
 */
class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    val pageList = ArrayList<Fragment>()

    init {
        pageList.add(LocalListFragment())
        pageList.add(TravelerListFragment())
        pageList.add(ChatRoomsFragment())
//        pageList.add(FeedsFragment())
        pageList.add(MoreInfoFragment())
    }

    override fun getItem(position: Int): Fragment {
        return pageList[position]
    }

    override fun getCount(): Int = pageList.size
}