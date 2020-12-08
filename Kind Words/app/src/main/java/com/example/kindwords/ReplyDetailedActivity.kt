@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate")

package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.database.FirebaseDatabase

/*
    this activity class provides a detailed view of a reply to the client
 */
class ReplyDetailedActivity : AppCompatActivity() {
    private lateinit var replyId: String

    private lateinit var textView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var replyText: String
    private var postText: String? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply_detailed)
        actionBar?.hide()
        textView = findViewById(R.id.textView)
        val subject = intent.getStringExtra("subject")
        val message = intent.getStringExtra("message")
        replyId = intent.getStringExtra("replyId") as String
        val recipientId = intent.getStringExtra("recipientId") as String
        val rating = intent.getStringExtra("rating") as String


        ratingBar = findViewById(R.id.ratingBar)
        ratingBar.rating = rating.toFloat()
        ratingBar.setOnRatingBarChangeListener{_, rat, _ -> ratingChange(rat)}
        replyText = subject + "\n\n" + message
        textView.text = replyText

        // Grab the post associated with the current reply
        val postsAdapter = PostsAdapter(applicationContext, postFilter = recipientId)
        MyPosts(postsAdapter)
        postsAdapter.postCountData.observe(this, Observer { count ->
            Log.i("TAG", recipientId)
            Log.i("detail", postsAdapter.count.toString())
            if (count == 1) {
                Log.i("TAG", "in post adapter if statement")
                val postItem = postsAdapter.getItem(0) as Post
                postText = postItem.subject + "\n\n" + postItem.message
            }
        })

    }

    // Go back to the replies list view
    fun viewMyReplies(view: View) {
        val i = Intent(this@ReplyDetailedActivity, MyRepliesActivity::class.java )
        startActivity(i)
    }

    // When the rating of the reply is changed, update tge database to reflect the changes
    private fun ratingChange(rating: Float) {
        FirebaseDatabase.getInstance().reference.child("replies").child(replyId).
        child("rating").setValue(rating.toString())
    }

    // report the current reply for abusive content
    fun reportReply(view: View) {
            val intent = Intent(this@ReplyDetailedActivity,
                CreateReplyReportActivity::class.java)
            intent.putExtra("replyId", replyId)

            startActivity(intent)

    }

    // switch the view from reply to post associated with the reply
    // So the client can view both original post and reply alternatively
    fun switchPostReply(view: View) {
        // the current text view is of the reply, switch to the post
        if (postText != null && textView.text == replyText) {
            textView.text = postText
            ratingBar.isEnabled = false
        }
        // if the current view is of the post, switch to the reply
        else if (postText != null && textView.text == postText) {
            textView.text = replyText
            ratingBar.isEnabled = true
        }
    }



}