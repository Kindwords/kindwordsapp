package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity(){


    private var currentPost: Post? = null
    private lateinit var storyTextView: TextView
    private lateinit var handFade: Animation
    lateinit var animationSticker: ImageView
    private lateinit var recentPost: RecentPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar_dashboard))
        supportActionBar?.title = "Home"

        storyTextView = findViewById(R.id.story)

        setAnimation()
        initializePostDownloader()
    }

    private fun initializePostDownloader() {
        // initialize posts downloader
        recentPost = RecentPost()
        // Observe for when the first post is downloaded
        recentPost.initComplete.observe(this, { _ -> updateView() })
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

    private fun setAnimation() {
        animationSticker = findViewById(R.id.next_post_animation)
        animationSticker.alpha = 0F
        handFade =  AnimationUtils.loadAnimation(this, R.anim.hand_fade)
        handFade.setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {

                    animationSticker.alpha = 0F

                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })

    }

    fun nextPost(view: View) {
        animationSticker.alpha = 1F
        animationSticker.startAnimation(handFade)
        updateView()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.dashboard -> true
            R.id.posts -> {startActivity(Intent(this@HomeActivity, MyPostsActivity::class.java))
                return true}
            R.id.replies -> {startActivity(Intent(this@HomeActivity, MyRepliesActivity::class.java))
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    // display more information pop up message
    fun signOut(menuItem: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun createNewPost(view: View) {
        startActivity(Intent(this@HomeActivity, CreatePostActivity::class.java))
    }

    fun createNewReply(view: View) {
        if (currentPost != null) {
            val intent = Intent(this@HomeActivity, CreateReplyActivity::class.java)
            intent.putExtra("postId", currentPost!!.postId)
            startActivity(intent)
        }
        else {
            Toast.makeText(applicationContext, "No Posts to reply to at the moment",
                Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val TAG = "TAG"
        val POSTID = "com.example.kindwords.postID"
    }


}