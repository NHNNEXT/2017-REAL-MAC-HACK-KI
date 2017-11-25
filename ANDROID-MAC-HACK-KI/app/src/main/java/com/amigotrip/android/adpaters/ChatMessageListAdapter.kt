package com.amigotrip.android.adpaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amigotrip.android.datas.ChatMessage
import com.amigotrip.anroid.R

/**
 * Created by Zimincom on 2017. 11. 25..
 */
class ChatMessageListAdapter : RecyclerView.Adapter<ChatMessageListAdapter.BindHolder>(){

    val messageList = arrayListOf<ChatMessage>()

    private val TYPE_MINE = 11
    private val TYPE_OTHER = 22

    init {
        messageList.add(ChatMessage(11, "hello", "mina"))
        messageList.add(ChatMessage(22, "bye", "mina"))
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindHolder {

        val holder: BindHolder
        val view: View

        val inflater = LayoutInflater.from(parent?.context)

        if (viewType == TYPE_OTHER) {
            view = inflater.inflate(R.layout.chat_row_others, parent, false)
            holder = OthersMessageHolder(view)
            return holder
        } else {
            view = inflater.inflate(R.layout.chat_row_mine, parent, false)
            holder = UserMessageHolder(view)
            return holder
        }

    }


    override fun onBindViewHolder(holder: BindHolder, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return messageList[position].type
    }

    fun addMessage(chatMessage: ChatMessage) {
        messageList.add(chatMessage)
        notifyItemInserted(messageList.size - 1)
    }

    inner class OthersMessageHolder(itemView: View): BindHolder(itemView){
        override fun bind(messageItem: ChatMessage) {
            itemView.findViewById<TextView>(R.id.tv_message).text = messageItem.message
        }
    }

    inner class UserMessageHolder(itemView: View) :  BindHolder(itemView){
        override fun bind(messageItem: ChatMessage) {
            itemView.findViewById<TextView>(R.id.tv_message).text = messageItem.message
        }
    }


    abstract class BindHolder(view: View) : RecyclerView.ViewHolder(view){
        abstract fun bind(messageItem: ChatMessage)
    }
}