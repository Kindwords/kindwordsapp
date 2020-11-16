package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager

class ViewMessagesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_messages)
        setSupportActionBar(findViewById(R.id.toolbar_view_messages))
        supportActionBar?.title = "Messages"

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
            R.id.messages -> true
            R.id.dashboard -> {startActivity(Intent(this@ViewMessagesActivity, DashboardActivity::class.java))
                true}
            R.id.replies -> {startActivity(Intent(this@ViewMessagesActivity, ViewRepliesActivity::class.java))
                true}
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun signOut(menuItem: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    }


