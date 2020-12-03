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

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var confirmPasswordView : TextView
    private lateinit var auth: FirebaseAuth
    private var validator = Validators()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.toolbar_register))

        emailTextView = findViewById(R.id.register_email)
        passwordTextView = findViewById(R.id.register_password)
        confirmPasswordView = findViewById(R.id.confirm_register_password)

        auth = FirebaseAuth.getInstance()


    }

    // register user account
    fun signUp(view: View) {
        val user = emailTextView.text.toString()
        val pass = passwordTextView.text.toString()
        val passConfirm = confirmPasswordView.text.toString()

        if (user.replace(" ", "") == "" || pass.replace(" ", "") == "") {
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
        else
        {
            Log.i(LoginActivity.TAG, "Attempting to Log register User")
            auth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) autoLogin()
                    else signUpFailed()
                }
        }
    }

    // auto login user so they don't need to log in again
    private fun autoLogin() {
        Log.i(LoginActivity.TAG, "Registeration successful")
        val user = emailTextView.text.toString()
        val pass = passwordTextView.text.toString()

        Log.i(LoginActivity.TAG, "Attempting to signIn user")
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    Log.i(LoginActivity.TAG, "signIn successful")
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java )
                    intent.putExtra(UID, auth.uid)
                    startActivity(intent)
                }
                else loginFailed()
            }


    }

    // redirect user to login page. Case where they don't have an account
    fun login(view: View) {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java ))
    }

    // Log details for testing
    //Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
    //Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()
    //                        progressBar!!.visibility = View.GONE

    private fun emptyLoginField() {
        Toast.makeText(applicationContext, "Please enter a non-empty Username & Password!", Toast.LENGTH_LONG).show()
        return
    }

    private fun failedPasswordConfirmation() {
        Toast.makeText(applicationContext, "Please make sure the passwords match!", Toast.LENGTH_LONG).show()
        return
    }

    private fun failedEmailValidation() {
        Toast.makeText(applicationContext, "Please enter a valid email!", Toast.LENGTH_LONG).show()
        return
    }

    private fun failedPasswordValidation() {
        Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
        return
    }
    private fun loginFailed() {
        Toast.makeText(applicationContext, "Invalid log-in credentials! Please try again.", Toast.LENGTH_LONG).show()
        return
    }

    private fun signUpFailed() {
        Toast.makeText(applicationContext, "Sign-up failed. Try a different email or wait a few minutes.", Toast.LENGTH_LONG).show()
        return
    }

    // create tool bar menu options
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
        val UID = "com.example.kindwords.uid"
    }
}