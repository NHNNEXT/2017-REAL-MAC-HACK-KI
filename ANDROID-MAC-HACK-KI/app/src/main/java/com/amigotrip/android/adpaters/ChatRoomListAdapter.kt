package com.amigotrip.android.adpaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.datas.ChatRoom
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.row_chatroom.view.*

/**
 * Created by Zimincom on 2017. 11. 20..
 */
class ChatRoomListAdapter : RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder>() {

    lateinit var chatRoomListener: OnChatRoomClickListener

    val chatRoomList = arrayListOf<ChatRoom>(
            ChatRoom(title = "hello", previewMessage =  "my name is mina"),
            ChatRoom(title = "hello", previewMessage =  "my name is mina"),
            ChatRoom(title = "hello", previewMessage =  "my name is mina")
    )

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_chatroom, parent,
                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(chatRoomList[position])
    }

    override fun getItemCount(): Int = chatRoomList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        fun bind(chatRoom: ChatRoom) {
            itemView.tv_room_title.text = chatRoom.title
            itemView.tv_chat_preview.text = chatRoom.previewMessage
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            chatRoomListener.onRoomClick(0)
        }
    }


    fun setOnRoomClickLisetener(onChatRoomClick: OnChatRoomClickListener) {
        this.chatRoomListener = onChatRoomClick
    }

    interface OnChatRoomClickListener {
        fun onRoomClick(position: Int)
    }
}