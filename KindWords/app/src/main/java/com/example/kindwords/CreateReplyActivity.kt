package com.example.kindwords

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateReplyActivity: AppCompatActivity() {
    private lateinit var mReply: Reply
    private lateinit var postAuthor: String
    private lateinit var subjectView: TextView
    private lateinit var bodyView: TextView
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reply)
        setSupportActionBar(findViewById(R.id.toolbar_create_reply))
        supportActionBar?.title = "Create A New Reply"

        subjectView = findViewById(R.id.create_reply_subject);
        bodyView = findViewById(R.id.create_reply_body);
        submitButton = findViewById(R.id.create_reply_send_button);

        submitButton.setOnClickListener(View.OnClickListener {
            addReplyToDatabase(it);
        });

        mReply = Reply()
        postAuthor = intent.getStringExtra(POSTID).toString()

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


    fun addReplyToDatabase(view: View) {
        if (validateFields()) {
            //TODO
            // create a new reply object with the subject, and body as arguments
            // use the mReply function addReplyToDatabse(new reply object, postAuthor) to add the reply to the database
            // toast message saying reply was successful

            var newReply = Reply (subjectView.text.toString(), bodyView.text.toString());
            mReply.addReplyToDataBase(newReply, postAuthor);

            Toast.makeText(applicationContext, "Post was successful!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Post was not successful. Make sure the subject" +
                    "and body are not empty!", Toast.LENGTH_LONG).show()
        }

    }

    companion object {
        val TAG = "TAG"
        val POSTID = "com.example.kindwords.postID"
    }
}