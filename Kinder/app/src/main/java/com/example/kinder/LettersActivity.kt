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

class LettersActivity : AppCompatActivity() {

    lateinit var story_text_view:TextView
    lateinit var fade:Animation
    lateinit var pray_sticker:ImageView
    lateinit var write_advice__sticker:ImageView
    var b = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter)
        actionBar?.hide()

        story_text_view = findViewById<LinearLayout>(R.id.main_linearLayout).findViewById<ScrollView>(R.id.story_scroll_view).findViewById<TextView>(R.id.story)
        fade = AnimationUtils.loadAnimation(this, R.anim.fade)
        pray_sticker = findViewById(R.id.pray_sticker)
        write_advice__sticker = findViewById(R.id.write_advice_sticker)

        pray_sticker.alpha = 0F
        write_advice__sticker.alpha = 0F

        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                pray_sticker.alpha = 0F

                /*

                if (b) {
                    story_text_view.alpha = 0.0F
                }
                else {
                    story_text_view.alpha = 1F
                }

                b = !b

                 */

            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    fun pray(view: View) {

        story_text_view.startAnimation(fade);
        pray_sticker.alpha = 1F
        pray_sticker.startAnimation(fade)

    }

    //writing a letter
    fun need_Advice(view: View) {
        var i = Intent(this@LettersActivity, needAdviceActivity::class.java )
        startActivity(i)
    }

    fun write_advice(view: View) {
        var i = Intent(this@LettersActivity, writeAdviceActivity::class.java )
        startActivity(i)

    }

    fun go_to_profile(view: View) {

        var i = Intent(this@LettersActivity, profileActivity::class.java )
        startActivity(i)

    }

    fun go_to_advice_list(view: View) {
        var i = Intent(this@LettersActivity, AdviceListActivity::class.java )
        startActivity(i)

    }



}