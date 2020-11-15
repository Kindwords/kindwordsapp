package com.example.kindwords

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import javax.security.auth.Subject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Post(
    var postId: String = "",
    var authorId: String = "",
    var subject: String = "",
    var message: String = "",
    var day: String = "",
    var time: String = "",
    var viewCount: Int = 0,
    var replyCount: Int = 0) {
    private var reference = FirebaseDatabase.getInstance().reference.child("posts")

    // initialization
    //--------------------------------------------------------------------------------------------
    init {
    }
    //--------------------------------------------------------------------------------------------

    // class attributes
    //---------------------------------------------------------------------------------------------
    @Exclude
    fun addPostToDataBase(){
        postId = (reference.push()).key.toString()
        reference.child(postId).setValue(this)}

    @Exclude
    fun deletePostFromDatabase() {
        reference.child(postId).removeValue()
    }

    @Exclude
    fun getDashBoardPosts(){}
    @Exclude
    fun getMyPosts(){}
    //---------------------------------------------------------------------------------------------



}


class Reply(uid: String = ""){
    // private attributes
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var dReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("replies").child(uid)
    private var replyId = uid

    // database attributes
    // var date:
    var seenStatus: Boolean = false

    init { //no need for listeners as contents of replies cant be modified
    }

    @Exclude
    fun addReply(){}

}