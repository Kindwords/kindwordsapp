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
    This activity class handles the registration process for a client
    It also automatically logs them into the newly created account
 */
class RegisterActivity : AppCompatActivity() {
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var confirmPasswordView : TextView
    private lateinit var auth: FirebaseAuth
    private var validator = Validators()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        actionBar?.hide()
        emailTextView = findViewById(R.id.register_email)
        passwordTextView = findViewById(R.id.register_password)
        confirmPasswordView = findViewById(R.id.confirm_register_password)

        auth = FirebaseAuth.getInstance()

    }

    // register a new account
    fun signUp(view: View) {
        val user = emailTextView.text.toString()
        val pass = passwordTextView.text.toString()
        val passConfirm = confirmPasswordView.text.toString()

        if (user.replace(" ", "") == ""
            || pass.replace(" ", "") == "") {
            emptyLoginField()
        }
        else if (pass != passConfirm) {
            failedPasswordConfirmation()
        }
        else if (!validator.validEmail(user)) {
            failedEmailValidation()
        }
        else if (!validator.validPassword(pass)) {
            failedPasswordValidation()
        }
        else {
            Log.i("TAG", "Attempting to Log register User")
            auth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) autoLogin()
                    else signUpFailed()
                }
        }
    }

    // auto login user so they don't need to log in again
    private fun autoLogin() {
        Log.i("TAG", "Registeration successful")
        val user = emailTextView.text.toString()
        val pass = passwordTextView.text.toString()

        Log.i("TAG", "Attempting to signIn user")
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    Log.i("TAG", "signIn successful")
                    val intent = Intent(this@RegisterActivity,
                        HomeActivity::class.java )
                    intent.putExtra("UID", auth.uid)
                    startActivity(intent)
                } else loginFailed()
            }
    }

    // redirect client to login page.
    fun login(view: View) {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java ))
    }

   // empty pass and email fields
    private fun emptyLoginField() {
        Toast.makeText(applicationContext, "Please enter a non-empty Username & Password!",
            Toast.LENGTH_LONG).show()
    }

    // pass and pass confirmation do not match
    private fun failedPasswordConfirmation() {
        Toast.makeText(applicationContext, "Please make sure the passwords match!",
            Toast.LENGTH_LONG).show()
    }

    // email does not appear to be in proper email format
    private fun failedEmailValidation() {
        Toast.makeText(applicationContext, "Please enter a valid email!",
            Toast.LENGTH_LONG).show()
    }

    // password does not meet password requirements
    private fun failedPasswordValidation() {
        Toast.makeText(applicationContext, "Invalid password!. Must be at least 1 letter,  " +
                "1 number, min 6 chars, max 8 chars", Toast.LENGTH_LONG).show()
    }

    // wrong login credentials
    private fun loginFailed() {
        Toast.makeText(applicationContext, "Invalid log-in credentials! Please try again.",
            Toast.LENGTH_LONG).show()
    }
    // can't signUp: issue connecting to database
    private fun signUpFailed() {
        Toast.makeText(applicationContext, "Sign-up failed. Try a different email " +
                "or wait a few minutes.", Toast.LENGTH_LONG).show()
    }
}