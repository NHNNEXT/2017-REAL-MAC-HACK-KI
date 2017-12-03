package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.amigotrip.android.adpaters.ChatMessageListAdapter
import com.amigotrip.android.datas.ChatMessage
import com.amigotrip.android.extentions.string
import com.amigotrip.anroid.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity(){

    val database = FirebaseDatabase.getInstance()
    val messageRef = database.getReference("messages")

    private val isBottom = false
    private lateinit var roomKey: String

    //push() 사용해서 방 목록 만들기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        val adapter = ChatMessageListAdapter()
        chats.adapter = adapter
        chats.layoutManager = layoutManager

        roomKey = intent.getStringExtra("roomKey")

        messageRef.child(roomKey).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(snapshot: DataSnapshot?, data: String?) {
                val message = snapshot!!.getValue(ChatMessage::class.java)
                adapter.addMessage(message!!)

                var lastPos = layoutManager.findLastVisibleItemPosition()
                Log.d("position", lastPos.toString())
                if(lastPos == adapter.itemCount - 1) {
                    chats.scrollToPosition(adapter.itemCount-1)
                } else {
                    chats.scrollToPosition(adapter.itemCount-1)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }
        })

        btn_send.setOnClickListener { sendMessage() }

    }

    private fun setRecyclerView() {

    }

    private fun sendMessage() {

        val message = input_message.string
        val messageKey = messageRef.child(roomKey).push().key

        messageRef.child(roomKey).child(messageKey).setValue(ChatMessage(11, message, "message"))
    }

}
