package com.example.kindwords

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth

class MyPostsActivity : AppCompatActivity() {


    private lateinit var listView: ListView
    private lateinit var myPosts: MyPosts
    lateinit var myPostsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_posts)
        actionBar?.hide()

        listView = findViewById<ListView>(R.id.list_view)


    }



    override fun onPause() {
        myPosts.unregisterListener()
        super.onPause()
    }

    override fun onResume() {
        myPostsAdapter = PostsAdapter(applicationContext, FirebaseAuth.getInstance().uid)
        myPosts = MyPosts(myPostsAdapter)
        listView.adapter = myPostsAdapter
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        myPosts.unregisterListener()
    }

    fun goToLetters(view: View) {

        var i = Intent(this@MyPostsActivity, HomeActivity::class.java )
        startActivity(i)

    }



}