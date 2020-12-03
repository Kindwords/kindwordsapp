package com.example.kinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if user is signed in, skip sign in process
        // else let them sign in
        if (auth.currentUser != null) { // user is signed in
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }
        else {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
        finish()

    }




}