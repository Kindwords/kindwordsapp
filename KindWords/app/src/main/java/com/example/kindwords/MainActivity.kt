package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if user is signed in, skip sign in process
        // else let them sign in
        //auth.signOut() // for testing until we implement the signout function
        if (auth.currentUser != null) { // user is signed in
            val dashboardActivityIntent =
                Intent(this@MainActivity, DashboardActivity::class.java)
            dashboardActivityIntent.putExtra(UID, auth.uid)
            startActivity(dashboardActivityIntent)
        }
        else {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }







    }

    companion object {
        val TAG = "TAG"
        val UID = "com.example.kindwords.uid"

    }

}