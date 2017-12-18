package com.amigotrip.android.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.support.v7.preference.PreferenceScreen
import android.widget.Toast
import com.amigotrip.android.AmigoApplication
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.activities.NewArticleActivity
import com.amigotrip.android.activities.StartActivity
import com.amigotrip.android.datas.User
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Zimincom on 2017. 11. 20..
 */
class MainPreferenceFragment : PreferenceFragmentCompat(), PreferenceManager
.OnPreferenceTreeClickListener, PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    lateinit var amigoService: AmigoService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)

        amigoService = (context.applicationContext as AmigoApplication).component.service()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == "pref_profile") {

        } else if (preference.key == "pref_logout") {
            signOut()
        } else if (preference.key == "pref_new_article") {

            amigoService.loginUser(UserInfoManager.getLogineduser())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<User>() {
                        override fun onError(e: Throwable) {
                            Toast.makeText(context,
                                    "you are not logined", Toast.LENGTH_SHORT).show()
                        }

                        override fun onComplete() {
                            val intent = Intent(context, NewArticleActivity::class.java)
                            startActivity(intent)
                        }

                        override fun onNext(user: User) {
                        }
                    })
        }

        return true
    }

    private fun signOut() {

        UserInfoManager.removeUser()

        startActivity(Intent(activity, StartActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }


    override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat?, pref: PreferenceScreen?): Boolean {
        caller?.preferenceScreen = pref
        return true
    }
}