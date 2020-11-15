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

class readAdviceActivity : AppCompatActivity() {

    lateinit var letter_or_post:TextView
    var letter = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)
        actionBar?.hide()
        letter_or_post = findViewById<LinearLayout>(R.id.linearlayout).findViewById<ScrollView>(R.id.scrollview).findViewById(R.id.letter_or_post)
        
    }

    fun go_to_advice_list(view: View) {
        var i = Intent(this@readAdviceActivity, AdviceListActivity::class.java )
        startActivity(i)
    }

    fun letter_or_advice(view: View) {

        if(letter)
        {
            letter_or_post.text = resources.getString(R.string.story_str)
        }
        else
        {
            letter_or_post.text = resources.getString(R.string.advice_str)
        }
        letter = !letter

    }


}