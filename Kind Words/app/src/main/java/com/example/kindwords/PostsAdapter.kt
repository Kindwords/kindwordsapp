package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
/*
 this adapter handles  the list views for posts/letters
 It also enables posts to be filtered by a certain criteria
 */
class PostsAdapter(mContext: Context, uidFilter: String? = null,
                   postFilter: String? = null) : BaseAdapter(){

     private var postList = ArrayList<Post>()
    var postCountData = MutableLiveData<Int>()

    private var mUidFilter: String? = uidFilter
    private var mPostIdFilter = postFilter

    init{postCountData.value = postList.size }
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
            newView = inflater!!.inflate(R.layout.post_item, parent, false)

            holder.textView = newView.findViewById(R.id.textView) as TextView

            newView.tag = holder
        } else {
            holder = newView?.tag as ViewHolder
        }
        holder.textView?.text = curr.subject
        // view post detailed
        holder.textView?.setOnClickListener {
            val intent = Intent(parent.context, PostDetailedActivity::class.java)
            intent.putExtra("subject", curr.subject)
            intent.putExtra("message", curr.message)
            parent.context.startActivity(intent)
        }

        return newView
    }


    internal class ViewHolder {
        var textView: TextView? = null

    }

    // filter by a postId
    // Useful when looking for a particular post regardless of author
    private fun filterByPost(listItem: Post): Boolean {
        return mPostIdFilter == null || mPostIdFilter == listItem.postId
    }

    // filter by the a post's author id. useful when looking for a post by a particular author
    private fun filterByUid(listItem: Post): Boolean {
        return mUidFilter == null || mUidFilter == listItem.authorId
    }

    fun add(listItem: Post) {
        // apply filters
        // can apply 0, 1 or more filters at the same time
        if (filterByPost(listItem) && filterByUid(listItem)) {
            postList.add(listItem)
            postCountData.value = postList.size
            notifyDataSetChanged()
        }
    }


    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }


}