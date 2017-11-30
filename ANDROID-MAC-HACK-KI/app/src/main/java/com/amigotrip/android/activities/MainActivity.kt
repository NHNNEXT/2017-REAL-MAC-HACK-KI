package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.adpaters.MainPagerAdapter
import com.amigotrip.anroid.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AHBottomNavigation.OnTabSelectedListener,
    ViewPager.OnPageChangeListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        val navigationAdapter = AHBottomNavigationAdapter(MainActivity@this, R.menu.nagivation)

        navigationAdapter.setupWithBottomNavigation(navigation)
        navigation.setOnTabSelectedListener(this)
        pager_main.setOnPageChangeListener(this)

        pager_main.adapter = MainPagerAdapter(supportFragmentManager)

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        navigation.currentItem = position
    }
    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        pager_main.setCurrentItem(position, false)
        return true
    }
}
