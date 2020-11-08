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

class AdviceListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice_list)
        actionBar?.hide()


    }

    fun go_to_letters(view: View) {

        var i = Intent(this@AdviceListActivity, LettersActivity::class.java )
        startActivity(i)

    }

    fun read_advice(view: View) {

        var i = Intent(this@AdviceListActivity, readAdviceActivity::class.java )
        startActivity(i)

    }


}