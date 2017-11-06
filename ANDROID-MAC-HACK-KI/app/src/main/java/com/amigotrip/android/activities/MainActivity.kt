package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.adpaters.MainPagerAdapter
import com.amigotrip.anroid.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AHBottomNavigation.OnTabSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        val navigationAdapter = AHBottomNavigationAdapter(MainActivity@this, R.menu.nagivation)

        navigationAdapter.setupWithBottomNavigation(navigation)
        navigation.setOnTabSelectedListener(this)
        pager_main.adapter = MainPagerAdapter(supportFragmentManager)

    }

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        pager_main.setCurrentItem(position, false)
        return true
    }
}
