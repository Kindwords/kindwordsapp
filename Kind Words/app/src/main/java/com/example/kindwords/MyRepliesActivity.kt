package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MyRepliesActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var myReplies: Replies
    private lateinit var myRepliesAdapter: RepliesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_replies)
        actionBar?.hide()

        listView = findViewById<ListView>(R.id.list_view)

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
        myRepliesAdapter = RepliesAdapter(applicationContext,
            recipientAuthorFilter = FirebaseAuth.getInstance().uid)
        myReplies = Replies(myRepliesAdapter)
        listView.adapter = myRepliesAdapter
        super.onResume()
    }

    fun goToHome(view: View) {
        startActivity(Intent(this@MyRepliesActivity, HomeActivity::class.java ))
    }


}