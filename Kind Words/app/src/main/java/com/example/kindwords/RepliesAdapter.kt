package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import java.io.Serializable
import kotlin.collections.ArrayList

class RepliesAdapter(mContext: Context, recipientFilter: String? = null,
                     recipientAuthorFilter: String? = null,
                    seenStatusFilter:Boolean? = null) : BaseAdapter(), Serializable{

    private var list = ArrayList<Reply>()
    var replyCountData = MutableLiveData<Int>()
    private var mRecipientFilter: String? = recipientFilter
    private var mAuthorFilter: String? = recipientAuthorFilter
    private var mSeenStatusFilter: Boolean? = seenStatusFilter

    init{replyCountData.value = list.size}
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        var newView = convertView

        val holder: ViewHolder
        val curr = list[position]
        if (null == convertView) {
            holder = ViewHolder()
            newView = inflater!!.inflate(R.layout.reply_item, parent, false)

            holder.textView = newView.findViewById(R.id.textView) as TextView
            holder.clickView = newView.findViewById(R.id.click_view) as ImageView
            holder.badgeIcon = newView.findViewById(R.id.reply_badge) as TextView
            newView.tag = holder
        } else {
            holder = newView?.tag as ViewHolder
        }
        holder.textView?.text = "${curr.subject}"
        // set badge
        if (curr.seenStatus) holder.badgeIcon?.visibility = View.GONE


        // when the reply badge is clicked, show the detailed reply
        holder.clickView?.setOnClickListener {
            val intent = Intent(parent.context, ReplyDetailedActivity::class.java)
            intent.putExtra("subject", curr.subject)
            intent.putExtra("message", curr.message)
            intent.putExtra("rating", curr.rating)
            intent.putExtra("replyId", curr.replyId)
            intent.putExtra("recipientId", curr.recipientPostId)
            parent.context.startActivity(intent)

            // if reply has not been seen by the user before, mark it as seen
            if (!curr.seenStatus) {
                curr.seenStatus = true
                curr.updateReplyAtDatabase()
            }


        }

        holder.textView?.setOnClickListener {
            val intent = Intent(parent.context, ReplyDetailedActivity::class.java)
            intent.putExtra("subject", curr.subject)
            intent.putExtra("message", curr.message)
            intent.putExtra("rating", curr.rating)
            intent.putExtra("replyId", curr.replyId)
            intent.putExtra("recipientId", curr.recipientPostId)
            parent.context.startActivity(intent)

            // if reply has not been seen by the user before, mark it as seen
            if (!curr.seenStatus) {
                curr.seenStatus = true
                curr.updateReplyAtDatabase()
            }


        }



        return newView
    }

    internal class ViewHolder {
        var textView: TextView? = null
        var clickView: ImageView? = null
        var badgeIcon: TextView? = null

    }


    private fun filterBySeenStatus(listItem: Reply): Boolean {
        return mSeenStatusFilter == null || mSeenStatusFilter == listItem.seenStatus
    }
    private fun filterbyRecipient(listItem: Reply): Boolean {
        return mRecipientFilter == null || mRecipientFilter == listItem.recipientPostId
    }

    private fun filterByAuthor(listItem: Reply): Boolean {
        return mAuthorFilter == null || mAuthorFilter == listItem.recipientAuthorId
    }


    fun add(listItem: Reply) {
        // apply one or more filters to the list adapter
        // discriminate posts on authorid, recipientid, or seen status
        if (filterByAuthor(listItem) && filterbyRecipient(listItem)
            && filterBySeenStatus(listItem)) {
            list.add(listItem)
            replyCountData.value = list.size
            notifyDataSetChanged()
        }
    }

    fun remove(index: Int) {
        list.removeAt(index)
        replyCountData.value = 100
        notifyDataSetChanged()
    }

    fun removeAllViews() {
        list.clear()
        notifyDataSetChanged()
    }

    fun setListAdapter(newList: ArrayList<Reply>) {
        list = ArrayList()
        for (reply in newList) list.add(reply)
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<Reply> {return list}

    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}