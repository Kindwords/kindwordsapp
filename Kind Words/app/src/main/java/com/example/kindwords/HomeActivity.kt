package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth

/*
    This activity class is the central board of the app. This is where a client;
     accesses all other related menu items
     views letters posted by other clients
     creates new letters
     replies to letters posted by clients
     reports letters posted by clients
 */
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
        storyTextView = findViewById(R.id.story)
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


    // setup a listener to notify a client of new letter replies
    private fun setUpBadgeNotificationListener(){5
        val repliesBadge = findViewById<TextView>(R.id.reply_badge)

        // adapter filters replies to just the replies received by this client,
        // and replies that have not been seen by this client
        val listenerAdapter = RepliesAdapter(applicationContext,
            recipientAuthorFilter = FirebaseAuth.getInstance().uid, seenStatusFilter = false)
        repliesBadgeObject = Replies(listenerAdapter)

        //observe a reply count live data to notify the client of exactly how many
        // new/unseen replies are yet to be seen
        listenerAdapter.replyCountData.observe(this, Observer { count ->
            Log.i("Badge Count", count.toString())
            repliesBadge.text = count.toString()
        })

    }

    // the post downloader begins the letter download process for all letters to be
    // presented to clients
    private fun initializePostDownloader() {
        // initialize posts downloader
        // Observe for when the first post is downloaded
        recentPost = RecentPost()
        recentPost.initComplete.observe(this, Observer{ res ->
            if(res == true) {
                updateView()
            } })
    }

    // update the view from one letter to the other
    @SuppressLint("SetTextI18n")
    private fun updateView() {
        // if there's a recent post, show it
        recentPost.getRecentPost()?.let {
            // hide reload post button
            findViewById<Button>(R.id.reload_posts).visibility = View.GONE
            currentPost = it
            val story = it.subject + "\n\n" + it.message
            storyTextView.text = story

            // if there's no recent post, give users the option to reply already seen posts
        } ?: run {
            findViewById<Button>(R.id.reload_posts).visibility = View.VISIBLE
            currentPost = null
            storyTextView.text = "\n\n\n\n\n\n\n\n\n No New Letters Available At The Moment"
            recentPost.initComplete.value = false
        }


    }

    // setup the letter swipe animation process
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

    // when the next letter (pray) button is clicked, show the next letter
    fun nextLetter(view: View) {
        currentPost?.let {
            it.viewCount += 1
            it.update() // update post at the database. I.E increase its seen count
        }
        storyTextView.startAnimation(fade)
        praySticker.alpha = 1F
        praySticker.startAnimation(handFade)
    }

    // no new letters are available, reload already seen letters
    fun reloadLetters(view: View) {
        initializePostDownloader()
    }

    //writing a letter
    fun createNewLetter(view: View) {
        startActivity(Intent(this@HomeActivity, CreatePostActivity::class.java ))
    }

    // reply to a letter
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

    // report a letter
    fun createReport(view: View) {
        if (currentPost != null) {
            val intent = Intent(this@HomeActivity, CreatePostReportActivity::class.java)
            intent.putExtra("postId", currentPost!!.postId)
            intent.putExtra("authorId", currentPost!!.authorId)
            startActivity(intent)
        }
        else {
            Toast.makeText(applicationContext, "No Letters to report",
                Toast.LENGTH_LONG).show()
        }
    }

    // sign out of the app
    fun signOut(view: View) {
        confirmDialogue()

    }

    // view client's replies from other clients
    fun viewMyReplies(view: View) {
        startActivity(Intent(this@HomeActivity, MyRepliesActivity::class.java ))
    }

    // view all letters posted by client
    fun viewMyPosts(view: View){
        startActivity(Intent(this@HomeActivity, MyPostsActivity::class.java ))
    }

    // display more information pop up message
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    // confirm sign out message
    private fun confirmDialogue() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are You Sure You Want To SignOut?")
            .setCancelable(false)
            .setPositiveButton("Confirm") { _, _ -> signOut() }
            // negative button text and action
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("SignOut Alert")
        // show alert dialog
        alert.show()
    }



}