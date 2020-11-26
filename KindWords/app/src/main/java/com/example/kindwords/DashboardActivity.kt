package com.example.kindwords

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.yuyakaido.android.cardstackview.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth


class DashboardActivity : AppCompatActivity(){

    lateinit var mPost : Post
    lateinit var mReply: Reply
     private var currentPost: Post? = null
    private lateinit var layoutManager: CardStackLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(findViewById(R.id.toolbar_dashboard))
        supportActionBar?.title = "Dashboard"
        mPost = Post()
        mReply = Reply()

        /* TESTING
        val reply1 = Reply("Test1", "This is a test1")
        reply1.rating = 5
        val reply2 = Reply("Test2", "This is a test2")
        reply2.rating = 1
        mReply.addReplyToDataBase(reply1, mPost)
        mReply.addReplyToDataBase(reply2, mPost)
        */
        currentPost = Post()


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
            R.id.dashboard -> true
            R.id.messages -> {startActivity(Intent(this@DashboardActivity, ViewMessagesActivity::class.java))
                return true}
            R.id.replies -> {startActivity(Intent(this@DashboardActivity, ViewRepliesActivity::class.java))
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    // display more information pop up message
    fun signOut(menuItem: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun createPost(view: View) {
        startActivity(Intent(this@DashboardActivity, CreatePostActivity::class.java))
    }
    fun createReply(view: View) {
        if (currentPost == null) {
            Toast.makeText(
                applicationContext,
                "No Posts to reply to at the moment",
                Toast.LENGTH_LONG
            ).show()
        }
        else {
            startActivity(
                Intent(this@DashboardActivity, CreateReplyActivity::class.java)
                    .putExtra(POSTID, currentPost!!.authorId)
            )
        }
    }

    companion object {
        val TAG = "TAG"
        val POSTID = "com.example.kindwords.postID"
    }


}