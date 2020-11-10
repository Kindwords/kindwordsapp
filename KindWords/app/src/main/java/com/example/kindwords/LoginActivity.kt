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

class LoginActivity : AppCompatActivity() {

    lateinit var userNameView : TextView
    lateinit var userPasswordView: TextView
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar_register))




    }
    // case where users do no have an account, redirect them to registration page
    fun createAccount(view: View) {
        Log.i(TAG, "Starting Create Account Activity")
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java ))
    }

    // log in a user
    fun login(view: View) {
        val user = "brianmile10@yahoo.com"
        val pass = "123456"
        Log.i(TAG, "attempting to log user in")
        auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                val intent = Intent(this@LoginActivity, DashboardActivity::class.java )
                intent.putExtra(UID, auth.uid)
                startActivity(intent)
            }
            else wrongPass()
        }



    }

    //Toast mesage informing user about wrong credentials
    //TODO display a toast telling the user they put in the wrong credentials
    private fun wrongPass(){
        Log.i(TAG, "Login failed. wrong credentials")
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