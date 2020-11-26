package com.example.kindwords

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ServerValue.TIMESTAMP
import java.util.*
import javax.security.auth.Subject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Post(var subject: String = "", var message: String = "", ) {

    var authorId = ""
    var postId : String = ""
    var viewCount: Int = 0
    var replyCount: Int = 0
    lateinit var time: Map<String, String>
    private var reference = FirebaseDatabase.getInstance().reference.child("posts")

    init { authorId = FirebaseAuth.getInstance().uid.toString() }

    @Exclude
    fun addPostToDataBase(post: Post){
        post.time = ServerValue.TIMESTAMP
        post.postId = (reference.push()).key.toString()
        post.reference.child(post.postId).setValue(post)}

    @Exclude
    fun deletePostFromDatabase(post: Post) {
        post.reference.child(post.postId).removeValue()
    }

    @Exclude
    fun getDashBoardPosts(){}
    @Exclude
    fun getMyPosts(){}
    //---------------------------------------------------------------------------------------------
}

class Reply(var subject: String = "", var message: String = "", ) {

    var authorId = ""
    var receipientId = ""
    var replyId : String = ""
    var rating: Int = 0
    var seenStatus: Boolean = false
    lateinit var time: Map<String, String>
    private var reference = FirebaseDatabase.getInstance().reference.child("replies")

    init { authorId = FirebaseAuth.getInstance().uid.toString() }

    @Exclude
    fun addReplyToDataBase(reply: Reply, postId: String){
        reply.time = ServerValue.TIMESTAMP
        reply.receipientId = postId
        reply.replyId = (reference.push()).key.toString()
        reply.reference.child(reply.replyId).setValue(reply)}

    @Exclude
    fun deleteReplyFromDatabase(reply: Reply) {
        reply.reference.child(reply.replyId).removeValue()
    }


    @Exclude
    fun getMyReplies(){}
    //---------------------------------------------------------------------------------------------
}



