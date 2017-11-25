package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.adpaters.ChatMessageListAdapter
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val adapter = ChatMessageListAdapter()

        chats.adapter = adapter

    }

}
