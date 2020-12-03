package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CreateReplyActivity: AppCompatActivity() {
    private lateinit var mReply: Reply
    private lateinit var postId: String
    private lateinit var subjectView: TextView
    private lateinit var messageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reply)
        setSupportActionBar(findViewById(R.id.toolbar_create_reply))
        supportActionBar?.title = "New Reply"
        subjectView = findViewById(R.id.create_reply_subject)
        messageView = findViewById(R.id.create_reply_message)

        //TODO: instantiate text views and button
        //
        mReply = Reply()
        postId = intent.getStringExtra("postId").toString()

    }


    private fun validateFields(): Boolean {
        if (subjectView.text.toString().replace(" ", "") == "" ||
            messageView.text.toString().replace(" ", "") == ""
        ) {
            return false;
        }
        return true;
    }


    fun addReplyToDatabase(view: View) {
        if (validateFields()) {
            //TODO
            // create a new reply object with the subject, and body as arguments
            // use the mReply function addReplyToDatabse(new reply object, postAuthor) to add the reply to the database
            // toast message saying reply was successful
            mReply.message = messageView.text.toString()
            mReply.subject = subjectView.text.toString()
            mReply.recipientId = postId
            mReply.addReplyToDataBase()
            Toast.makeText(applicationContext, "Reply was successful!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Reply was not successful. Make sure the subject" +
                    "and body are not empty!", Toast.LENGTH_LONG).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.dashboard -> {startActivity(Intent(this@CreateReplyActivity, HomeActivity::class.java))
                return true}
            R.id.posts -> {startActivity(Intent(this@CreateReplyActivity, MyPostsActivity::class.java))
                return true}
            R.id.replies -> {startActivity(Intent(this@CreateReplyActivity, MyRepliesActivity::class.java))
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    // display more information pop up message
    fun signOut(menuItem: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }




    companion object {
        val TAG = "TAG"
        const val POSTID = "com.example.kindwords.postID"
    }
}