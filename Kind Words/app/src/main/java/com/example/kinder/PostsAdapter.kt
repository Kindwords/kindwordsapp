package com.example.kinder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.collections.ArrayList

class PostsAdapter(mContext: Context, uidFilter: String? = null,
                   postFilter: String? = null) : BaseAdapter() {

     private var postList = ArrayList<Post>()
    var postCountData = MutableLiveData<Int>()
    init{postCountData.value = postList.size }
    private var mUidFilter: String? = uidFilter
    private var mPostIdFilter = postFilter

    override fun getCount(): Int {
        return postList.size as Int
    }

    override fun getItem(position: Int): Any {
        return postList.get(position) as Post
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var newView = convertView
        val holder: ViewHolder
        val curr = postList[position] as Post

        if (null == convertView) {
            holder = ViewHolder()
            newView = inflater!!.inflate(R.layout.post_item, parent, false)

            holder.textView = newView.findViewById(R.id.textView) as TextView

            newView.tag = holder
        } else {
            holder = newView?.tag as ViewHolder
        }
        holder.textView?.text = "${curr.subject}"
        // view post detailed
        holder.textView?.setOnClickListener {
            val intent = Intent(parent.context, PostDetailedActivity::class.java)
            intent.putExtra("subject", curr.subject)
            intent.putExtra("message", curr.message)
            parent.context.startActivity(intent)
        }

        //todo on long click, delete the post and associated replies (delete replies first)
        holder.textView?.setOnLongClickListener{
            true
        }

        return newView
    }

    internal class ViewHolder {
        var textView: TextView? = null

    }

    private fun filterByPost(listItem: Post): Boolean {
        return mPostIdFilter == null || mPostIdFilter == listItem.postId
    }

    private fun filterByUid(listItem: Post): Boolean {
        return mUidFilter == null || mUidFilter == listItem.authorId
    }
    fun add(listItem: Post) {
        if (filterByPost(listItem) && filterByUid(listItem)) {
            postList.add(listItem)
            postCountData.value = postList.size
            notifyDataSetChanged()
        }
    }


    fun removeAllViews() {
        postList.clear()
        notifyDataSetChanged()
    }

    fun setList(newList: ArrayList<Post>) {
        postList = ArrayList()
        for (post in newList) postList.add(post)
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<Post> {return postList}

    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }


}