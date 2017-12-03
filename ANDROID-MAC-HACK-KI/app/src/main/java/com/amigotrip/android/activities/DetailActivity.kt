package com.amigotrip.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amigotrip.android.UserInfoManager
import com.amigotrip.anroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    private lateinit var targetEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        targetEmail = intent.getStringExtra("email")
        Timber.d(targetEmail)


        iv_chat.setOnClickListener { createChatRoom() }
    }

    private fun createChatRoom() {
        //and add infos. need refctor

        //query my rooms


//        if (UserInfoManager.getChaters().contains(targetEmail)) {
//            Timber.d("you already have room")
//            return
//        }
//
//        UserInfoManager.addChater(targetEmail)

        val chatroomsRef = FirebaseDatabase.getInstance().getReference("rooms")


        val roomKey = chatroomsRef.push().key
        val loginedUserKey = UserInfoManager.getUserFirebaseKey()

        val userRef = FirebaseDatabase.getInstance().getReference("users")

        //find user firebase key by targetEmail
        //todo if i already have room, do not make room


        //add room info to user data
        userRef.child(loginedUserKey).child("rooms").child(roomKey).setValue(true)


        userRef.orderByChild("email")
                .equalTo(targetEmail)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                snapshot?.children?.forEach { snapshot ->
                                    chatroomsRef
                                            .child(roomKey)
                                            .child(snapshot.key)
                                            .setValue(true)
                                }
                            }
                        })


        chatroomsRef.child(roomKey).child(UserInfoManager.getUserFirebaseKey()).setValue(true)

        //todo put users info to intent
        val intent = Intent(this, ChatRoomActivity::class.java)
        intent.putExtra("roomKey", roomKey)
        startActivity(intent)
    }


}
