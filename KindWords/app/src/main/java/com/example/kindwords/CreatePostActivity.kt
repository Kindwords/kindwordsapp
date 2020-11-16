package com.example.kindwords

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CreatePostActivity: AppCompatActivity() {
    private lateinit var mPost : Post
    private lateinit var subjectView: TextView
    private lateinit var bodyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        setSupportActionBar(findViewById(R.id.toolbar_create_post))
        supportActionBar?.title = "Create A New Post"
        //TODO: instantiate text views and button
        //
        mPost = Post()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_title_only, menu)
        return true
    }

    //TODO
    // check to make sure the subject and body fields of the post are not empty
    private fun validateFields(): Boolean {return true}


    fun addPostToDatabase(view: View) {
        if (validateFields()) {
            //TODO
            // create a new post object with the subject, and body as arguments
            // use mPost function addPostToDatabase(newpost) to add the post to the database
            // toast message saying post was successful
            finish()
        } else {
            //TODO
            // Toast message telling user to fill in subject and body text views
        }

    }

}