package com.amigotrip.android.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.activities.IntroActivity
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.fragment_profile.*

class MoreInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_out.setOnClickListener { signOut() }
    }

    private fun signOut() {

        val pref = activity.getSharedPreferences(getString(R.string.KEY_PREFERENCE), Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(getString(R.string.KEY_ISSIGNIN), false)
        editor.apply()

        startActivity(Intent(activity, IntroActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

}
