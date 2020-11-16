package com.example.kindwords

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CreateReplyActivity: AppCompatActivity() {
    private lateinit var mReply: Reply
    private lateinit var postAuthor: String
    private lateinit var subjectView: TextView
    private lateinit var bodyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reply)
        setSupportActionBar(findViewById(R.id.toolbar_create_reply))
        supportActionBar?.title = "Create A New Reply"
        //TODO: instantiate text views and button
        //
        mReply = Reply()
        postAuthor = intent.getStringExtra(POSTID).toString()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_title_only, menu)
        return true
    }

    //TODO
    // check to make sure the subject and body fields of the post are not empty
    private fun validateFields(): Boolean {return true}


    fun addReplyToDatabase(view: View) {
        if (validateFields()) {
            //TODO
            // create a new reply object with the subject, and body as arguments
            // use the mReply function addReplyToDatabse(new reply object, postAuthor) to add the reply to the database
            // toast message saying reply was successful
            finish()
        } else {
            //TODO
            // Toast message telling user to fill in subject and body text views
        }

    }

    companion object {
        val TAG = "TAG"
        val POSTID = "com.example.kindwords.postID"
    }
}