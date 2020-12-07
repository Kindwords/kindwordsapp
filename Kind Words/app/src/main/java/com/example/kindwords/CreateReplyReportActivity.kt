package com.example.kindwords

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
    Report a reply for offensive content
 */
class CreateReplyReportActivity : AppCompatActivity() {
    private lateinit var mReport : Report
    private lateinit var replyId : String
    private lateinit var subjectView: TextView
    private lateinit var messageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reply_report)
        actionBar?.hide()
        subjectView = findViewById(R.id.subject)
        messageView = findViewById(R.id.message)
         replyId = intent.getStringExtra("replyId") as String

    }

    // ensure all fields of the reply body are filled up
    private fun validateFields(): Boolean {
        if (subjectView.text.toString().replace(" ", "") == "" ||
            messageView.text.toString().replace(" ", "") == ""
        ) { return false; }
        return true
    }

    // submit the report to the database
    fun addReportToDatabase(view: View) {
        if (validateFields()) {
            mReport = Report(subjectView.text.toString() , messageView.text.toString())
            mReport.submitReplyReport(replyId)
            Toast.makeText(applicationContext, "Report was successful!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "report was not successful. Make sure the subject" +
                    "and body are not empty!", Toast.LENGTH_LONG).show()
        }

    }

    // return to the reply detailed view
    fun finish(view: View) {
        finish()
    }


}