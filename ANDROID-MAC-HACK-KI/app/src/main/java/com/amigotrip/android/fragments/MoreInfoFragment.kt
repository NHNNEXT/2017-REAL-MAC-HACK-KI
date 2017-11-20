package com.amigotrip.android.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.UserInfoManager
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

        setUserInfo()
        btn_sign_out.setOnClickListener { signOut() }
    }

    private fun setUserInfo() {

        val preferences = UserInfoManager.getPreference()

        val name = preferences.getString(getString(R.string.KEY_USER_NAME), "no name")
        val email = preferences.getString(getString(R.string.KEY_USER_EMAIL), "no email")

        tv_name.text = name
        tv_email.text = email

    }

    private fun signOut() {

        UserInfoManager.removeUser()

        startActivity(Intent(activity, IntroActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

}
