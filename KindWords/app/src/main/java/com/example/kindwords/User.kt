package com.example.kindwords

import android.util.Log
import com.google.firebase.database.*
import java.util.*


class User(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) {
    private var dReference: DatabaseReference = reference

    var posts = ArrayList<Post>()
    init { //setup database listeners

        // setup post listeners
            dReference.child("posts").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    dataSnapshot.getValue(Post::class.java)?.let { post ->
                        posts.add(post)
                        Log.i("TAG", "Post Added")
                        5

                    }

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    @Exclude //from database data
    fun getReference() = dReference

    @Exclude //from database data
    fun addPost(){
        val key = (dReference.child("posts").push()).key.toString() // generate a new key
        val post = Post(dReference.child("posts").child(key)) // create a new post object
        dReference.child("posts").child(key).setValue(post) // add the post to the databse

    }
}

class Post(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) {
    var replies = ArrayList<Reply>()
    private var dReference: DatabaseReference = reference
    var date: Dates = Dates()
    var viewCount: Int = 0
    var replyCount: Int = 0

    init { //setup database listeners
        dReference.child("replies").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(Reply::class.java)?.let { reply ->
                    replies.add(reply)
                    Log.i("TAG", "reply added")
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {
            }


        })
    }

    @Exclude
    fun getReference() = dReference

    @Exclude
    fun addReply() {
        val key = (dReference.child("replies").push()).key.toString()
        val reply = Reply(dReference.child("replies").child(key))
        dReference.child("replies").child(key).setValue(reply)
    }
    fun incrementPostViewCount() {}
    fun incrementPostReplyCount() {}
}

class Dates {
    var day: String = ""
    var time: String = ""

}
class Reply(reference: DatabaseReference = FirebaseDatabase.getInstance().reference){
    private var dReference = reference
    var date: Dates = Dates()
    var seenStatus: Boolean = false

    init { //no need for listeners as contents of replies cant be modified
            }

}