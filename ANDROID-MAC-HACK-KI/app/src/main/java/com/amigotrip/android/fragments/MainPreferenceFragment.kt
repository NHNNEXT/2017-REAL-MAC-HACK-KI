package com.amigotrip.android.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.support.v7.preference.PreferenceScreen
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.activities.StartActivity
import com.amigotrip.anroid.R

/**
 * Created by Zimincom on 2017. 11. 20..
 */
class MainPreferenceFragment : PreferenceFragmentCompat(), PreferenceManager
.OnPreferenceTreeClickListener,PreferenceFragmentCompat.OnPreferenceStartScreenCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        if (preference?.key == "pref_profile") {

        } else if (preference?.key == "pref_logout") {
            signOut()
        }

        return true
    }

    private fun signOut() {

        UserInfoManager.removeUser()

        startActivity(Intent(activity, StartActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }


    override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat?, pref: PreferenceScreen?): Boolean {
        caller?.setPreferenceScreen(pref)
        return true
    }
}