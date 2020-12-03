package com.example.kinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    var already_registered = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            already_registered = true
        }
    }

    override fun onStart() {
        super.onStart()

        //after user register there is no need for login
        if (!already_registered)
        {
            var i = Intent(this@MainActivity, LoginActivity::class.java )
            startActivity(i)
        }
        else
        {
            var i = Intent(this@MainActivity, LettersActivity::class.java )
            startActivity(i)
        }
    }




}