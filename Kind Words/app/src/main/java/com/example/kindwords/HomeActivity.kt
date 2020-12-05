package com.example.kindwords

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
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
    private lateinit var repliesBadgeObject: Replies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        actionBar?.hide()
        storyTextView = findViewById<TextView>(R.id.story)
        setUpAnimation()
        initializePostDownloader()
    }

    override fun onResume() {
        setUpBadgeNotificationListener()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        repliesBadgeObject.unregisterListener()
    }



    private fun setUpBadgeNotificationListener(){
        val repliesBadge = findViewById<TextView>(R.id.reply_badge)
        val listenerAdapter = RepliesAdapter(applicationContext,
            recipientAuthorFilter = FirebaseAuth.getInstance().uid, seenStatusFilter = false)
        repliesBadgeObject = Replies(listenerAdapter)
        listenerAdapter.replyCountData.observe(this, Observer { count ->
            Log.i("Badge Count", count.toString())
            repliesBadge.text = count.toString()
        })

    }

    private fun initializePostDownloader() {
        // initialize posts downloader
        // Observe for when the first post is downloaded
        recentPost = RecentPost()
        recentPost.initComplete.observe(this, Observer{ res ->
            if(res == true) {
                updateView()
            } })
    }

    private fun updateView() {
        recentPost.getRecentPost()?.let {
            // hide reload post button
            findViewById<Button>(R.id.reload_posts).visibility = View.GONE
            currentPost = it
            val story = it.subject + "\n\n" + it.message
            storyTextView.text = story

        } ?: run {
            findViewById<Button>(R.id.reload_posts).visibility = View.VISIBLE
            storyTextView.text = "\n\n\n\n\n\n\n\n\n No New Letters Available At The Moment"
            recentPost.initComplete.value = false
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

                updateView() // update the view when the animation ends

            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }
    fun nextLetter(view: View) {
        currentPost?.let {
            it.viewCount += 1
            it.update() // update post at the database. I.E increase its seen count
        }
        storyTextView.startAnimation(fade);
        praySticker.alpha = 1F
        praySticker.startAnimation(handFade)
    }

    fun reloadLetters(view: View) {
        initializePostDownloader()
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