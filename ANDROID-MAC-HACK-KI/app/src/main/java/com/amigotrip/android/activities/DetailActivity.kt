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


        iv_chat.setOnClickListener { startChatWith(targetEmail) }
    }

    private fun startChatWith(targetEmail: String) {
        //and add infos. need refctor

        //query my rooms
        val userRef = FirebaseDatabase.getInstance().getReference("users")

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

        val chatroomsRef = FirebaseDatabase.getInstance().getReference("rooms")
        val loginedUserKey = UserInfoManager.getUserFirebaseKey()
        val targetUserKey = snapshot?.key

        Timber.d(targetUserKey)

        chatroomsRef
                .orderByChild(loginedUserKey)
                .equalTo(true)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onCancelled(err: DatabaseError?) {
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                //result = rooms which iam in
                                //snapshot = list which local user locate

                                //todo if partner is false???
                                val isRoomExist =
                                        snapshot!!.children!!.any { children -> children.hasChild(targetUserKey)}


                                if (isRoomExist) {

                                    val roomkey =
                                            snapshot!!.children!!.filter { children -> children
                                                    .hasChild(targetUserKey)
                                            }.map {
                                                children -> children.key
                                            }.first()

                                    Timber.d(roomkey)
                                    startChatActivity(roomkey)
                                    return
                                }
                                else {
                                    //if there is no room
                                    creteNewRoom(targetUserKey)
                                }

                            }
                        })


    }

    private fun creteNewRoom(targetUserKey: String?) {
        val chatroomsRef = FirebaseDatabase.getInstance().getReference("rooms")
        val roomKey = chatroomsRef.push().key

        chatroomsRef.child(roomKey).child(UserInfoManager.getUserFirebaseKey()).setValue(true)
        chatroomsRef.child(roomKey).child(targetUserKey).setValue(true)
        startChatActivity(roomKey)
    }

    private fun startChatActivity(roomKey: String) {
        //todo put users info to intent
        val intent = Intent(this, ChatRoomActivity::class.java)
        intent.putExtra("roomKey", roomKey)
        startActivity(intent)
    }


}
