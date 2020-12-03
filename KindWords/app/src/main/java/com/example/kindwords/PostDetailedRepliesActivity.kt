package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class PostDetailedRepliesActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var myRepliesAdapter: MyRepliesAdapter
    lateinit var replies: Replies

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detailed_replies)
        setSupportActionBar(findViewById(R.id.toolbar_dashboard))
        supportActionBar?.title = "Replies"


        val postId = intent.getStringExtra("postId")

        // check to see if the post/letter has any replies
        listView = findViewById(R.id.post_detailed_list_view)
        myRepliesAdapter = MyRepliesAdapter(applicationContext, recipientFilter = postId)
        replies = Replies(myRepliesAdapter)
        listView.adapter = myRepliesAdapter

        
    }

    override fun onDestroy() {
        replies.unregisterListener()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        replies.unregisterListener()
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
        replies.unregisterListener()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goToReplies() {
        startActivity(Intent(this@PostDetailedRepliesActivity, MyRepliesActivity::class.java))
        replies.unregisterListener()
        finish()
    }
    private fun goToHome(){
        startActivity(Intent(this@PostDetailedRepliesActivity, HomeActivity::class.java))
        replies.unregisterListener()
        finish()

    }
    private fun goToMessages(){
        startActivity(Intent(this@PostDetailedRepliesActivity, MyPostsActivity::class.java))
        replies.unregisterListener()
        finish()

    }



}