package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MyRepliesActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    lateinit var myReplies: Replies
    lateinit var myRepliesAdapter: MyRepliesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_replies)
        setSupportActionBar(findViewById(R.id.toolbar_my_replies))
        supportActionBar?.title = "My Replies"

        listView = findViewById<ListView>(R.id.my_replies_list_view)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onPause() {
        myReplies.unregisterListener()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        myRepliesAdapter = MyRepliesAdapter(applicationContext,
            authorFilter = FirebaseAuth.getInstance().uid)
        myReplies = Replies(myRepliesAdapter)
        listView.adapter = myRepliesAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.dashboard -> {goToHome(); true}
            R.id.posts -> {goToPosts(); true}
            R.id.replies -> true

            else -> super.onOptionsItemSelected(item)
        }
    }
    // display more information pop up message
    fun signOut(menuItem: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToPosts() {
        startActivity(Intent(this@MyRepliesActivity, MyPostsActivity::class.java))
        finish()
    }
    private fun goToHome(){
        startActivity(Intent(this@MyRepliesActivity, HomeActivity::class.java))
        finish()
    }

}