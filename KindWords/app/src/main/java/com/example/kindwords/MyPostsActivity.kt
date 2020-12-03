package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MyPostsActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    lateinit var myPosts: MyPosts
    lateinit var myPostsAdapter: MyPostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_posts)
        setSupportActionBar(findViewById(R.id.toolbar_dashboard))
        supportActionBar?.title = "My Letters"

        listView = findViewById<ListView>(R.id.list_view)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onPause() {
        myPosts.unregisterListener()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        myPostsAdapter = MyPostsAdapter(applicationContext, FirebaseAuth.getInstance().uid)
        myPosts = MyPosts(myPostsAdapter)
        listView.adapter = myPostsAdapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.dashboard -> {goToHome(); true}
            R.id.posts -> true
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
        startActivity(Intent(this@MyPostsActivity, MyRepliesActivity::class.java))
        finish()
    }
    private fun goToHome(){
        startActivity(Intent(this@MyPostsActivity, HomeActivity::class.java))
        finish()
    }



}