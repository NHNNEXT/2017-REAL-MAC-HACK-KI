package com.amigotrip.android.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.activities.NewPartyActivity
import com.amigotrip.android.adpaters.LocalListAdapter
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.fragment_local_list.*


/**
 * A simple [Fragment] subclass.
 */
class LocalListFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_local_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_locals.adapter = LocalListAdapter()

                //btn_new_post.setOnClickListener { writeNewPost()}

    }

    private fun writeNewPost() {
        startActivity(Intent(activity, NewPartyActivity::class.java))
    }

}
