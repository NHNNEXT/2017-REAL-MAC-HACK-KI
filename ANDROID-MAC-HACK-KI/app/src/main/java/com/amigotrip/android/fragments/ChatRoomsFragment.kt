package com.amigotrip.android.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.activities.ChatRoomActivity
import com.amigotrip.android.adpaters.ChatRoomListAdapter
import com.amigotrip.anroid.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_chats.*

//chatrooms fragment
class ChatRoomsFragment : Fragment(), ChatRoomListAdapter.OnChatRoomClickListener {

    var database = FirebaseDatabase.getInstance()

    lateinit var roomRef :  DatabaseReference

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomRef = database.getReference()

        val adapter = ChatRoomListAdapter()
        adapter.setOnRoomClickLisetener(this)
        list_chat.adapter = adapter

        if (!UserInfoManager.isUserLogin()) {
//            list_chat.visibility = View.INVISIBLE
        }

    }

    override fun onRoomClick(position: Int) {
        val intent = Intent(context, ChatRoomActivity::class.java)
        startActivity(intent)
    }

}
