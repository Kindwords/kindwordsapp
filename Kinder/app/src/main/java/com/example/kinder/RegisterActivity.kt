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

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)
        actionBar?.hide()

    }

    fun Sign_Up(view: View) {

    }
    fun Login(view: View) {
        var i = Intent(this@RegisterActivity, LoginActivity::class.java )
        startActivity(i)
    }


}