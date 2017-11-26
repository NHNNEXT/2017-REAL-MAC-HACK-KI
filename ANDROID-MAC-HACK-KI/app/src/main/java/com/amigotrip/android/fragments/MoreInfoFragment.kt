package com.amigotrip.android.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.UserInfoManager
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.pref_profile.*

class MoreInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (UserInfoManager.isUserLogin()) {
            setUserInfo()
        } else {
            tv_name.text = "please sign in!"
        }
    }

    private fun setUserInfo() {
        val user = UserInfoManager.getLogineduser()
        tv_name.text = user.name
    }


}
