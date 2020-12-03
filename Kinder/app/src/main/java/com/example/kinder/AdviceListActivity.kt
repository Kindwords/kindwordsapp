package com.example.kinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*

class AdviceListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice_list)
        actionBar?.hide()

        var lv = findViewById<ListView>(R.id.list_view)

        lv.adapter = customAdapter( this, R.layout.adviceitem, resources.getStringArray(R.array.adivce), this, false)


    }

    fun go_to_letters(view: View) {

        var i = Intent(this@AdviceListActivity, LettersActivity::class.java )
        startActivity(i)

    }


}