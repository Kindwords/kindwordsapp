package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
/*
    Create A new letter Activity
 */

class CreatePostActivity : AppCompatActivity() {
    private lateinit var mPost : Post
    private lateinit var subjectView: TextView
    private lateinit var messageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        actionBar?.hide()
        mPost = Post()
        subjectView = findViewById(R.id.subject)
        messageView = findViewById(R.id.message)
    }

    // Ensure that all fields of the letter are filled
    private fun validateFields(): Boolean {
        if (subjectView.text.toString().replace(" ", "") == "" ||
            messageView.text.toString().replace(" ", "") == ""
        ) { return false; }
        return true
    }

    // submit the letter to the database
    fun addPostToDatabase(view: View) {
        if (validateFields()) {
            mPost.subject = subjectView.text.toString()
            mPost.message = messageView.text.toString()
            mPost.addPostToDataBase()
            Toast.makeText(applicationContext, "Post was successful!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Post was not successful. Make sure the subject" +
                    "and body are not empty!", Toast.LENGTH_LONG).show()
        }

    }

    // return to the home page
    fun goToHome(view: View) {
        val i = Intent(this@CreatePostActivity, HomeActivity::class.java )
        startActivity(i)
    }


}