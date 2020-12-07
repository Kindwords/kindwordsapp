package com.example.kindwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

/*
    This Activity class handles the login action for each client/user
    It also gives users the option to register is they do not already have an account
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var userNameView : TextView
    private lateinit var userPasswordView: TextView
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        actionBar?.hide()
        userNameView = findViewById(R.id.email)
        userPasswordView = findViewById(R.id.password)
    }

    // create a new account
    fun createAccount(view: View) {
        Log.i("TAG", "Starting Create Account Activity")
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java ))
    }

    // log into an existing account
    fun login(view: View) {
        val user = userNameView.text.toString()
        val pass = userPasswordView.text.toString()

        // pass and username are empty strings
        if (user.replace(" ", "") == ""
            || pass.replace(" ", "") == "") {
            emptyLoginField()
        } else {
            Log.i("TAG", "attempting to log user in")
            auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else wrongPass()
            }
        }
    }

    // pass and username are empty
    private fun emptyLoginField() {
        Toast.makeText(applicationContext, "Please enter a non-empty Username & Password!",
            Toast.LENGTH_LONG).show()
        return
    }

    // wrong password was provided by the client
    private fun wrongPass(){
        Log.i("TAG", "Login failed. wrong credentials")
        Toast.makeText(applicationContext, "Invalid log-in credentials! Please try again.",
            Toast.LENGTH_LONG).show()
    }

}