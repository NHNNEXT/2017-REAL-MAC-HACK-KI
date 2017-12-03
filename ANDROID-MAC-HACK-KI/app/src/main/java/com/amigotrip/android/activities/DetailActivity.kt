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

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        email = intent.getStringExtra("email")
        Timber.d(email)


        iv_chat.setOnClickListener { createChatRoom() }
    }

    private fun createChatRoom() {
        val chatroomsRef = FirebaseDatabase.getInstance().getReference("rooms")

        val roomKey = chatroomsRef.push().key
        val loginedUser = UserInfoManager.getLogineduser()

        val userRef = FirebaseDatabase.getInstance().getReference("users")

        //find user firebase key by email
        //todo if i already have room, do not make room
        userRef.orderByChild("email")
                .equalTo(email)
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
