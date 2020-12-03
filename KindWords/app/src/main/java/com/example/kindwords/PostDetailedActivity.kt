package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class PostDetailedActivity : AppCompatActivity() {
    lateinit var postId: String
    private lateinit var textView: TextView
    lateinit var listView: ListView
    lateinit var myRepliesAdapter: MyRepliesAdapter
    lateinit var replies: Replies

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detailed)
        textView = findViewById(R.id.post_detailed_textView)
        setSupportActionBar(findViewById(R.id.toolbar_dashboard))
        supportActionBar?.title = "My Letter"

        val subject = intent.getStringExtra("subject")
        val message = intent.getStringExtra("message")
        postId = intent.getStringExtra("postId") as String

        // set subject and date
        textView.text = subject + "\n\n" + message

    }

    fun viewReplies(view: View) {
            val intent = Intent(this@PostDetailedActivity, PostDetailedRepliesActivity::class.java)
            intent.putExtra("postId", postId)
            startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
            R.id.dashboard -> {goToHome(); true}
            R.id.posts -> {goToMessages(); true}
            R.id.replies -> {goToReplies(); return true}

            else -> super.onOptionsItemSelected(item)
        }
    }
    // display more information pop up message
    fun signOut(menuItem: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToReplies() {
        startActivity(Intent(this@PostDetailedActivity, MyRepliesActivity::class.java))
        replies.unregisterListener()
        finish()
    }
    private fun goToHome(){
        startActivity(Intent(this@PostDetailedActivity, HomeActivity::class.java))
        replies.unregisterListener()
        finish()

    }
    private fun goToMessages(){
        startActivity(Intent(this@PostDetailedActivity, MyPostsActivity::class.java))
        replies.unregisterListener()
        finish()

    }



}