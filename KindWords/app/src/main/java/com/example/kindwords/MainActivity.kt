package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO
        // this is where we decide where to send the client
        // right now we just send them to the register activity
        startActivity(Intent(this@MainActivity, LoginActivity::class.java ))

    }


}