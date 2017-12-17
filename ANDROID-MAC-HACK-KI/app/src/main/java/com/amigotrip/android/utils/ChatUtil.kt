package com.amigotrip.android.utils

import android.app.Activity
import android.content.Intent
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.activities.ChatRoomActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import timber.log.Timber

/**
 * Created by Zimincom on 2017. 12. 4..
 */
//context
class ChatUtil(private val activity: Activity){

    private val database = FirebaseDatabase.getInstance()
    private var userRef = database.getReference("users")
    private var roomRef = database.getReference("rooms")
    private lateinit var targetEmail: String

    fun startChatWith(targetEmail: String) {
        //and add infos. need refctor
        //query my rooms
        this.targetEmail = targetEmail
        //query target key
        userRef.orderByChild("email")
                .equalTo(targetEmail)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                snapshot?.children?.forEach {
                                    snapshot -> findRooms(snapshot)
                                }
                            }
                        })


    }


    private fun findRooms(snapshot: DataSnapshot?) {
        Timber.d("rooms filter")

        val loginedUserKey = UserInfoManager.getUserFirebaseKey()
        val targetUserKey = snapshot?.key

        Timber.d(targetUserKey)

        roomRef
                .orderByValue()
                .orderByChild(loginedUserKey)
                .equalTo(true)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onCancelled(err: DatabaseError?) {
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                //result = rooms which iam in
                                //snapshot = list which local user locate

                                viewChat(snapshot, targetUserKey)
                            }
                        })
    }


    private fun viewChat(snapshot: DataSnapshot?, targetUserKey: String?) {
        val isRoomExist =
                snapshot!!.children!!.any { children -> children.hasChild(targetUserKey)}


        if (isRoomExist) {

            val roomkey =
                    snapshot!!.children!!.filter {
                        children -> children.hasChild(targetUserKey)
                    }.map {
                        children -> children.key
                    }.first()

            Timber.d(roomkey)
            startChatActivity(roomkey)
        } else {
            //if there is no room
            startNewChatAcitity(targetUserKey)
        }
    }


    private fun startNewChatAcitity(targetUserKey: String?) {

        val roomKey = roomRef.push().key
        val loginedUserKey = UserInfoManager.getUserFirebaseKey()
        val loginedUserEmail = UserInfoManager.getLogineduser().email

        roomRef.child(roomKey).child(UserInfoManager.getUserFirebaseKey()).setValue(true)
        roomRef.child(roomKey).child(targetUserKey).setValue(true)

        //add info to logined user
        val userChatInfoRef = userRef.child(loginedUserKey).child("chaters").child(roomKey)
        userChatInfoRef.child("email").setValue(targetEmail)
        userChatInfoRef.child("roomKey").setValue(roomKey)

        //add info to partner user
        val partnerChatInfoRef = userRef.child(targetUserKey).child("chaters").child(roomKey)
        partnerChatInfoRef.child("email").setValue(loginedUserEmail)
        partnerChatInfoRef.child("roomKey").setValue(roomKey)

        startChatActivity(roomKey)
    }


    private fun startChatActivity(roomKey: String) {
        //todo put users info to intent
        val intent = Intent(activity, ChatRoomActivity::class.java)
        intent.putExtra("roomKey", roomKey)
        activity.startActivity(intent)
    }

}