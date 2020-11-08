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

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        actionBar?.hide()

    }

    fun login(view: View) {

        var i = Intent(this@LoginActivity, LettersActivity::class.java )
        startActivity(i)

    }

    fun Forgot_Password(view: View) {

    }

    fun Create_Account(view: View) {

        var i = Intent(this@LoginActivity, RegisterActivity::class.java )
        startActivity(i)

    }


}