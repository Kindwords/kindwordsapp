package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlin.collections.ArrayList

class MyPostsAdapter(mContext: Context, uidFilter: String? = null,
                     postFilter: String? = null) : BaseAdapter() {

    private var postList = ArrayList<Post>()
    private var replyList = ArrayList<Post>()
    private var mUidFilter: String? = uidFilter
    private var mPostIdFilter = postFilter

    override fun getCount(): Int {
        return postList.size
    }

    override fun getItem(position: Int): Any {
        return postList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var newView = convertView
        val holder: ViewHolder
        val curr = postList[position]

        if (null == convertView) {
            holder = ViewHolder()
            newView = inflater!!.inflate(R.layout.postitem, parent, false)

            holder.subjectView= newView.findViewById(R.id.subject_text) as TextView
            holder.dateView = newView.findViewById(R.id.date_text) as TextView
            holder.itemView = newView.findViewById(R.id.item_view) as View
            newView.tag = holder
        } else {
            holder = newView?.tag as ViewHolder
        }
        holder.subjectView?.text = "${curr.subject}"
        holder.dateView?.text = "${curr.time}"
        // view post detailed
        holder.itemView?.setOnClickListener {
            val intent = Intent(parent.context, PostDetailedActivity::class.java)
            intent.putExtra("subject", curr.subject)
            intent.putExtra("message", curr.message)
            intent.putExtra("postId", curr.postId)
            parent.context.startActivity(intent)
        }

        //todo on long click, delete the post and associated replies (delete replies first)
        holder.itemView?.setOnLongClickListener{
            true
        }

        return newView
    }

    internal class ViewHolder {
        var subjectView: TextView? = null
        var dateView: TextView? = null
        var itemView: View? = null
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