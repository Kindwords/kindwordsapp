package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

/*
 This activity class displays all the replies sent to the client
 I.E replies to the clients letters/posts
 */

class MyRepliesActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var myReplies: Replies
    private lateinit var myRepliesAdapter: RepliesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_replies)
        actionBar?.hide()

        listView = findViewById(R.id.list_view)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        myReplies.unregisterListener()
    }
    override fun onPause() {
        super.onPause()
        myReplies.unregisterListener()
    }

    override fun onResume() {
        // filter replies to replies sent to the currently signed in client
        myRepliesAdapter = RepliesAdapter(applicationContext,
            recipientAuthorFilter = FirebaseAuth.getInstance().uid)
        myReplies = Replies(myRepliesAdapter)
        listView.adapter = myRepliesAdapter
        super.onResume()
    }

    // go to the homepage
    fun goToHome(view: View) {
        startActivity(Intent(this@MyRepliesActivity, HomeActivity::class.java ))
    }


}