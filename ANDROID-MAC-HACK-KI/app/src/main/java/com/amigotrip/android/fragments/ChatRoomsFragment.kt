package com.amigotrip.android.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.activities.ChatRoomActivity
import com.amigotrip.android.adpaters.ChatRoomListAdapter
import com.amigotrip.android.datas.ChatRoom
import com.amigotrip.anroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_chats.*

//chatrooms fragment
class ChatRoomsFragment : Fragment(), ChatRoomListAdapter.OnChatRoomClickListener {

    var database = FirebaseDatabase.getInstance()

    var userRef = database.getReference("users")
    val firebaseKey = UserInfoManager.getUserFirebaseKey()

    lateinit var adapter: ChatRoomListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_search_room.setOnClickListener {
            view -> (view as EditText).isCursorVisible = true
        }

    }

    override fun onResume() {
        super.onResume()
        initRecycler()
        adapter.setOnRoomClickLisetener(this)
    }

    override fun onRoomClick(chatRoom: ChatRoom) {
        val intent = Intent(context, ChatRoomActivity::class.java)
        intent.putExtra("roomKey", chatRoom.key)
        startActivity(intent)
    }

    private fun initRecycler() {
        if (UserInfoManager.isUserLogin()) {
            list_chat.visibility = View.VISIBLE
            tv_empty_list.visibility = View.INVISIBLE
        }

        adapter = ChatRoomListAdapter()
        adapter.setOnRoomClickLisetener(this)
        list_chat.adapter = adapter

        queryUserRooms()
    }

    private fun queryUserRooms() {
        userRef.child(firebaseKey)
                .child("chaters")
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        snapshot?.children?.forEach {
                            //add list
                            child -> adapter.addRoom(
                                ChatRoom(key = child.child("roomKey").value.toString(),
                                        title = child.child("email").value.toString()))
                        }
                    }
                })
    }

}
