package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class CreateReplyActivity : AppCompatActivity() {
    private lateinit var mReply: Reply
    private lateinit var postId: String
    private lateinit var authorId: String
    private lateinit var subjectView: TextView
    private lateinit var messageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reply)
        actionBar?.hide()
        subjectView = findViewById(R.id.subject)
        messageView = findViewById(R.id.message)
        mReply = Reply()
        postId = intent.getStringExtra("postId").toString()
        authorId = intent.getStringExtra("authorId").toString()

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
            mReply.message = messageView.text.toString()
            mReply.subject = subjectView.text.toString()
            mReply.recipientPostId = postId
            mReply.recipientAuthorId = authorId
            mReply.addReplyToDataBase()
            Toast.makeText(applicationContext, "Reply was successful!",
                Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(
                applicationContext, "Reply was not successful. Make sure the subject" +
                        "and body are not empty!", Toast.LENGTH_LONG
            ).show()
        }
    }


        fun goToHome(view: View) {
        startActivity(Intent(this@CreateReplyActivity, HomeActivity::class.java ))
        }



}