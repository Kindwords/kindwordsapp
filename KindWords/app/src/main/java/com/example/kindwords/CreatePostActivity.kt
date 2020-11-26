package com.example.kindwords

import android.R.attr.button
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CreatePostActivity: AppCompatActivity() {
    private lateinit var mPost : Post
    private lateinit var subjectView: TextView
    private lateinit var bodyView: TextView
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        setSupportActionBar(findViewById(R.id.toolbar_create_post))
        supportActionBar?.title = "Create A New Post"

        subjectView = findViewById(R.id.create_post_subject);
        bodyView = findViewById(R.id.create_post_body);
        submitButton = findViewById(R.id.create_post_send_button);

        submitButton.setOnClickListener(View.OnClickListener {
           addPostToDatabase(it);
        })

        mPost = Post()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_title_only, menu)
        return true
    }


    private fun validateFields(): Boolean {
        if (subjectView.text.toString().replace(" ", "") == "" ||
            bodyView.text.toString().replace(" ", "") == ""
        ) {
            return false;
        }
        return true;
    }


    fun addPostToDatabase(view: View) {
        if (validateFields()) {

            var newPost = Post(subjectView.text.toString(), bodyView.text.toString());
            mPost.addPostToDataBase(newPost);

            Toast.makeText(applicationContext, "Post was successful!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Post was not successful. Make sure the subject" +
                    "and body are not empty!", Toast.LENGTH_LONG).show()
        }

    }

}