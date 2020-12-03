package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var userNameView : TextView
    lateinit var userPasswordView: TextView
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar_register))

        userNameView = findViewById(R.id.email);
        userPasswordView = findViewById(R.id.password);
    }
    // case where users do no have an account, redirect them to registration page
    fun createAccount(view: View) {
        Log.i(TAG, "Starting Create Account Activity")
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java ))
    }

    // log in a user
    fun login(view: View) {
        val user = userNameView.text.toString()
        val pass = userPasswordView.text.toString()
        if (user.replace(" ", "") == "" || pass.replace(" ", "") == "") {
            emptyLoginField()
        } else {
            Log.i(TAG, "attempting to log user in")
            auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else wrongPass()
            }
        }
    }

    private fun emptyLoginField() {
        Toast.makeText(applicationContext, "Please enter a non-empty Username & Password!", Toast.LENGTH_LONG).show()
        return
    }

    private fun wrongPass(){
        Log.i(TAG, "Login failed. wrong credentials")
        Toast.makeText(applicationContext, "Invalid log-in credentials! Please try again.", Toast.LENGTH_LONG).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_register_login, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val TAG = "TAG"
        const val UID = "com.example.kindwords.uid"
    }
}