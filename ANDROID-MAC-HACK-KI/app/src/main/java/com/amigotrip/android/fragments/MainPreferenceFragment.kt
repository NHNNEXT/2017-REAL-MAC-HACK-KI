package com.amigotrip.android.fragments

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.amigotrip.anroid.R

/**
 * Created by Zimincom on 2017. 11. 20..
 */
class MainPreferenceFragment : PreferenceFragmentCompat(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }
}