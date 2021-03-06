package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
/*
    This activity class shows a detailed view of a post to the client
 */
class PostDetailedActivity : AppCompatActivity() {
    private lateinit var textView: TextView


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detailed)
        actionBar?.hide()
        textView = findViewById(R.id.post_detailed_textView)
        val subject = intent.getStringExtra("subject")
        val message = intent.getStringExtra("message")

        // set subject and date
        textView.text = subject + "\n\n" + message
    }

    // return to the list view of posts
    fun goToMyPosts(view: View) {
       // val i = Intent(this@PostDetailedActivity, MyPostsActivity::class.java )
        //startActivity(i)
        finish()
    }


}