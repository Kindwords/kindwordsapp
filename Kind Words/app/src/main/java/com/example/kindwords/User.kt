package com.example.kindwords

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
/*
    These group of objects handle requests to and from the database
 */

/*
    This class handles request for all letters posted by the client
 */
class MyPosts(PostsAdapter: PostsAdapter) {
    private var childEventListener: ChildEventListener? = null
    private var reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child("posts")

    init {
        childEventListener = reference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.value as HashMap<*, *>
                    val newPost = Post()
                    newPost.authorId = data["authorId"] as String
                    newPost.subject = data["subject"] as String
                    newPost.message = data["message"] as String
                    newPost.postId = data["postId"] as String
                    newPost.replyCount = (data["replyCount"] as Long).toInt()
                    newPost.viewCount = (data["viewCount"] as Long).toInt()
                    newPost.time = data["time"] as String
                    Log.i("POSTADD", "ADDing post")
                    PostsAdapter.add(newPost)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun unregisterListener() { childEventListener?.let{reference.removeEventListener(it)} }

}

/*
    This object handles all replies sent to the client
 */
class Replies (RepliesAdapter: RepliesAdapter){
    private var childEventListener: ChildEventListener? = null
    private var reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child("replies")

    init {
        childEventListener =
        reference.orderByChild("seenStatus").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.value as HashMap<*, *>
                    val newReply = Reply()
                    newReply.authorId = data["authorId"] as String
                    newReply.recipientPostId = data["recipientPostId"] as String
                    newReply.recipientAuthorId = data["recipientAuthorId"] as String
                    newReply.subject = data["subject"] as String
                    newReply.message = data["message"] as String
                    newReply.replyId = data["replyId"] as String
                    newReply.rating = data["rating"] as String
                    newReply.seenStatus = data["seenStatus"] as Boolean
                    newReply.time = data["time"] as String
                    RepliesAdapter.add(newReply)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}


            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun unregisterListener() {childEventListener?.let{reference.removeEventListener(it)}}


}

/*
    This object handles the preview of letters in the home page
 */
class RecentPost {
    private var posts: Queue<Post>? = LinkedList()
    var initComplete = MutableLiveData<Boolean>()
    private val uid: String? = FirebaseAuth.getInstance().uid
    private var reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child("posts")

    init {
        initComplete.value = false
        reference.orderByChild("viewCount").addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data = snapshot.value as HashMap<*, *>
                    if (data["authorId"] != uid) {
                        val newPost = Post()
                        newPost.authorId = data["authorId"] as String
                        newPost.subject = data["subject"] as String
                        newPost.message = data["message"] as String
                        newPost.postId = data["postId"] as String
                        newPost.replyCount = (data["replyCount"] as Long).toInt()
                        newPost.viewCount = (data["viewCount"] as Long).toInt()
                        newPost.time = data["time"] as String
                        (posts as LinkedList<Post>).add(newPost)
                        // let the home page show the first letter preview once its available
                        if (!initComplete.value!!) initComplete.value = true
                    }

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    // called by the homepage to check if at least one letter has been downloaded and is
    // ready for preview
    fun getRecentPost(): Post? {
        return if (posts?.size == 0) null
        else posts?.remove()
    }
}

/*
 The post object formats return data from the database for an easy and orderly  access
 It also handles the database post requests to add and update posts to the data base
 */
class Post(var subject: String = "", var message: String = "" ): Serializable {

    var authorId = FirebaseAuth.getInstance().uid.toString()
    var postId : String = ""
    var viewCount: Int = 0
    var replyCount: Int = 0
    var time: String = ""
    private var reference = FirebaseDatabase.getInstance().reference.child("posts")

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    @Exclude
    fun addPostToDataBase() {
        if (postId == "") {
            val date = getCurrentDateTime()
            time = date.toString("yyyy/MM/dd HH:mm:ss")
            postId = (reference.push()).key.toString()
            reference.child(postId).setValue(this)
        }
    }

    @Exclude
    fun update() {
        reference.child(postId).setValue(this) }

}

/*
 The reply object formats return data from the database for an easy and orderly  access
 It also handles the database reply requests to add and update reply to the data base
 */
class Reply(var subject: String = "", var message: String = ""): Serializable {

    var authorId = FirebaseAuth.getInstance().uid.toString()
    var recipientPostId = ""
    var recipientAuthorId = ""
    var replyId: String = ""
    var rating: String = "0.0"
    var seenStatus: Boolean = false
    lateinit var time: String
    private var reference = FirebaseDatabase.getInstance().reference.child("replies")

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    @Exclude
    fun addReplyToDataBase() {
        if (replyId == "") {
            val date = getCurrentDateTime()
            time = date.toString("yyyy/MM/dd HH:mm:ss")
            replyId = (reference.push()).key.toString()
            reference.child(replyId).setValue(this)
        }
    }

    @Exclude
    fun updateReplyAtDatabase() {
        reference.child(replyId).setValue(this)
    }

    //---------------------------------------------------------------------------------------------

}

/*
 The report object handles letter/post and reply reports to the database
 */
class Report( Subject: String, Body: String) {
    var subject: String = Subject
    var message: String = Body
    private var postId: String = ""
    private var authorId: String = ""
    private var replyId: String = ""
    private var reference = FirebaseDatabase.getInstance().reference.child("reports")

    fun submitPostReport(postID: String, authorID: String) {
        postId = postID
        authorId = authorID
        val key = (reference.push()).key.toString()
        reference.child(key).setValue(this)

    }

    fun submitReplyReport(replyId: String) {
        this.replyId = replyId
        val key = (reference.push()).key.toString()
        reference.child(key).setValue(this)
    }
}



