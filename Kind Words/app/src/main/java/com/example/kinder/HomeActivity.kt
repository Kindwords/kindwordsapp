package com.example.kinder

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {

    private lateinit var storyTextView:TextView
    private lateinit var fade:Animation
    private lateinit var handFade:Animation
    lateinit var praySticker:ImageView
    private lateinit var writeAdviceSticker:ImageView
    private var currentPost: Post? = null
    private lateinit var recentPost: RecentPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        actionBar?.hide()

        storyTextView = findViewById<TextView>(R.id.story)
        setUpAnimation()
        initializePostDownloader()

    }


    private fun initializePostDownloader() {
        // initialize posts downloader
        recentPost = RecentPost()
        // Observe for when the first post is downloaded
        recentPost.initComplete.observe(this, Observer{ _ -> updateView() })
    }

    private fun updateView() {
        recentPost.getRecentPost()?.let {
            currentPost = it
            val story = it.subject + "\n\n" + it.message
            storyTextView.text = story
            it.viewCount += 1
            it.update()
        }
    }

    private fun setUpAnimation() {
        fade = AnimationUtils.loadAnimation(this, R.anim.fade)
        handFade =  AnimationUtils.loadAnimation(this, R.anim.hand_fade)

        praySticker = findViewById(R.id.pray_sticker)
        writeAdviceSticker = findViewById(R.id.write_advice_sticker)

        praySticker.alpha = 0F
        writeAdviceSticker.alpha = 0F

        handFade.setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {

                    praySticker.alpha = 0F

                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })


        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                updateView()

            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }
    fun nextLetter(view: View) {
        storyTextView.startAnimation(fade);
        praySticker.alpha = 1F
        praySticker.startAnimation(handFade)
    }

    //writing a letter
    fun createNewLetter(view: View) {
        var i = Intent(this@HomeActivity, CreatePostActivity::class.java )
        startActivity(i)
    }

    fun replyToLetter(view: View) {
        if (currentPost != null) {
            val intent = Intent(this@HomeActivity, CreateReplyActivity::class.java)
            intent.putExtra("postId", currentPost!!.postId)
            intent.putExtra("authorId", currentPost!!.authorId)
            startActivity(intent)
        }
        else {
            Toast.makeText(applicationContext, "No Letters to reply to",
                Toast.LENGTH_LONG).show()
        }

    }

    fun createReport(view: View) {
        if (currentPost != null) {
            val intent = Intent(this@HomeActivity, CreateReportActivity::class.java)
            intent.putExtra("postId", currentPost!!.postId)
            intent.putExtra("authorId", currentPost!!.authorId)
            startActivity(intent)
        }
        else {
            Toast.makeText(applicationContext, "No Letters to report",
                Toast.LENGTH_LONG).show()
        }
    }

    fun signOut(view: View) {
        confirmDialogue()

    }

    fun viewMyReplies(view: View) {
        var i = Intent(this@HomeActivity, MyRepliesActivity::class.java )
        startActivity(i)

    }
    fun viewMyPosts(view: View){
        var i = Intent(this@HomeActivity, MyPostsActivity::class.java )
        startActivity(i)

    }

    // display more information pop up message
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }
    private fun confirmDialogue() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are You Sure You Want To SignOut?")
            .setCancelable(false)
            .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ -> signOut()})
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel() })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("SignOut Alert")
        // show alert dialog
        alert.show()
    }



}