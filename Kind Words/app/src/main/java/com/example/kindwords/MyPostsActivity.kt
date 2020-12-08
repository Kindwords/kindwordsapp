package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/*
    This activity brings up a listview of all the posts/letters created by the client
 */
class MyPostsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var myPosts: MyPosts
    private lateinit var myPostsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_posts)
        actionBar?.hide()

        listView = findViewById(R.id.list_view)
    }

    // unregister incoming letter listener
    override fun onPause() {
        myPosts.unregisterListener()
        super.onPause()
    }

    // establish new letter listener and setup the list adapter
    // filter posts/letters to just letters posted by the currently signed in client
    override fun onResume() {
        myPostsAdapter = PostsAdapter(applicationContext, uidFilter = FirebaseAuth.getInstance().uid)
        myPosts = MyPosts(myPostsAdapter)
        listView.adapter = myPostsAdapter
        super.onResume()
    }

    // unregister incoming posts listener on back pressed
    override fun onBackPressed() {
        super.onBackPressed()
        myPosts.unregisterListener()
        finish()
    }

    // go to the home page
    fun goToLetters(view: View) {
        //startActivity(Intent(this@MyPostsActivity, HomeActivity::class.java ))
        myPosts.unregisterListener()
        finish()
    }



}