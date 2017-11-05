package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.anroid.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val item1 = AHBottomNavigationItem("홈", android.R.drawable.ic_menu_help, R.color
                .colorPrimary)

        val item2 = AHBottomNavigationItem("홈", android.R.drawable.ic_menu_help, R.color
                .colorPrimary)
        val item3 = AHBottomNavigationItem("홈", android.R.drawable.ic_menu_help, R.color
                .colorPrimary)
        val item4 = AHBottomNavigationItem("홈", android.R.drawable.ic_menu_help, R.color
                .colorPrimary)
        val item5 = AHBottomNavigationItem("홈", android.R.drawable.ic_menu_help, R.color
                .colorPrimary)

        navigation.addItem(item1)
        navigation.addItem(item1)
        navigation.addItem(item1)
        navigation.addItem(item1)
        navigation.addItem(item1)
    }
}
