package com.example.kindwords

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.yuyakaido.android.cardstackview.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DashboardActivity : AppCompatActivity(){

    lateinit var userNameView : TextView
    lateinit var userPasswordView: TextView
    private lateinit var layoutManager: CardStackLayoutManager
    private lateinit var uid: String
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(findViewById(R.id.toolbar_dashboard))
        uid = intent.getStringExtra(UID)!!
        databaseReference = FirebaseDatabase.getInstance().getReference("users/$uid")
        databaseReference.setValue("hello World")

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val UID = "com.example.kindwords.uid"
    }


}