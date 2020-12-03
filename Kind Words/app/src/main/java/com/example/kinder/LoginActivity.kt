package com.example.kinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        actionBar?.hide()
        userNameView = findViewById(R.id.email);
        userPasswordView = findViewById(R.id.password);
    }

    // case where users do no have an account, redirect them to registration page
    fun createAccount(view: View) {
        Log.i("TAG", "Starting Create Account Activity")
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java ))
    }

    // log in a user
    fun login(view: View) {
        val user = userNameView.text.toString()
        val pass = userPasswordView.text.toString()
        if (user.replace(" ", "") == "" || pass.replace(" ", "") == "") {
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

    private fun emptyLoginField() {
        Toast.makeText(applicationContext, "Please enter a non-empty Username & Password!", Toast.LENGTH_LONG).show()
        return
    }

    private fun wrongPass(){
        Log.i("TAG", "Login failed. wrong credentials")
        Toast.makeText(applicationContext, "Invalid log-in credentials! Please try again.", Toast.LENGTH_LONG).show()
    }


    fun forgotPassword(view: View) {

    }




}