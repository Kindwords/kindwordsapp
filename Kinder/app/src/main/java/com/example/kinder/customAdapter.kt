package com.example.kinder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class customAdapter(context: Context, resource: Int, str: Array<String>, activity: Activity, post:Boolean) :

    ArrayAdapter<String>(context,resource,str) {

    private var mlayoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mactivity = activity
    private var id = resource
    private var post = post

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val newView: View

        if(convertView == null)
        {

            newView = mlayoutInflater.inflate(id, parent, false)

            val ViewHolder = ViewHolder()
            newView.tag = ViewHolder
            ViewHolder.textView = newView.findViewById(R.id.textView)

        }
        else
        {
            newView = convertView
        }

        val storedViewHolder = newView.tag as  ViewHolder
        storedViewHolder.textView.text = getItem(position)
        storedViewHolder.textView.setOnClickListener {

            if(post) {
                var i = Intent(mactivity, readPostActivity::class.java)
                mactivity.startActivity(i)
            }
            else {
                var i = Intent(mactivity, readAdviceActivity::class.java)
                mactivity.startActivity(i)
            }
        }

        return newView



    }

    internal class ViewHolder{
        lateinit var textView: TextView

    }



}