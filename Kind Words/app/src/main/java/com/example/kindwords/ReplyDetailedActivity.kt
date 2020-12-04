package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.firebase.database.FirebaseDatabase

class ReplyDetailedActivity : AppCompatActivity() {
    lateinit var replyId: String

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

        val postsAdapter = PostsAdapter(applicationContext, postFilter = recipientId)
        postsAdapter.postCountData.observe(this, Observer { count ->
            Log.i("TAG", recipientId.toString())
            Log.i("detail", postsAdapter.count.toString())
            if (count == 1) {
                Log.i("TAG", "in post adapter if statement")
                val postItem = postsAdapter.getItem(0) as Post
                postText = postItem.subject + "\n\n" + postItem.message
            }
        })
        //todo unregister post listener
        val post = MyPosts(postsAdapter)




    }

    fun viewMyReplies(view: View) {
        val i = Intent(this@ReplyDetailedActivity, MyRepliesActivity::class.java )
        startActivity(i)
    }

    private fun ratingChange(rating: Float) {
        FirebaseDatabase.getInstance().reference.child("replies").child(replyId).
        child("rating").setValue(rating.toString())
    }

    fun switchPostReply(view: View) {
        if (postText != null && textView.text == replyText) {
            textView.text = postText
            ratingBar.isEnabled = false
        }
        else if (postText != null && textView.text == postText) {
            textView.text = replyText
            ratingBar.isEnabled = true
        }
    }



}