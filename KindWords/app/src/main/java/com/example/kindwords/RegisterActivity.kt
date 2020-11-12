package com.example.kindwords

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var confirmPasswordView : TextView
    private lateinit var auth: FirebaseAuth

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
        // TODO: verify that user and pass are not empty strings
        // TODO: check that both pass and passConfirm match
        // TODO: validate username and password using validators

        // Hint use if statements for all the cases and put the code below in the final else case
        // Call Toast methods to let user know of issues

        Log.i(LoginActivity.TAG, "Attempting to Log register User")
        auth.createUserWithEmailAndPassword(user, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    autoLogin()
                }
                else signUpFailed()
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
                    val intent = Intent(this@RegisterActivity, DashboardActivity::class.java )
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
    //TODO: toast message telling user password failed
    private fun loginFailed() {}
    //TODO: toast message telling user signup failed
    private fun signUpFailed() {}
    //TODO: toast message telling user passwords do not much
    private fun passwordsNoMatch() {}
    //TODO: toast message telling user they need to input the correct credentials
    private fun invalidCredentials() {}


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