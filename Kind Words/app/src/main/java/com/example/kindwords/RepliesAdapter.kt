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

/*
    this adapter handles  the list views for replies to letters/posts
    It also enables letters to be filtered by a certain criteria
 */
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
        holder.textView?.text = curr.subject
        // set badge
        if (curr.seenStatus) holder.badgeIcon?.visibility = View.GONE


        // when the reply badge is clicked, show the detailed view of the reply
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
        // duplicated view for viewing the details of a reply
        // duplicated to account for multiple click regions
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

    // filter reply by its seen status
    // used for notifying clients of new/unseen replies
    private fun filterBySeenStatus(listItem: Reply): Boolean {
        return mSeenStatusFilter == null || mSeenStatusFilter == listItem.seenStatus
    }

    // filter a reply by its recipient post
    // used for grabbing the post the reply replied to
    private fun filterByRecipient(listItem: Reply): Boolean {
        return mRecipientFilter == null || mRecipientFilter == listItem.recipientPostId
    }

    // filter a reply by the author of the post the reply replied to
    // Useful for reporting a reply, grabbing a post the reply replied to, viewing all replies
    // sent to a particular client, etc
    private fun filterByAuthor(listItem: Reply): Boolean {
        return mAuthorFilter == null || mAuthorFilter == listItem.recipientAuthorId
    }


    fun add(listItem: Reply) {
        // apply one or more filters to the list adapter
        // discriminate posts on authorid, recipientid, or seen status
        if (filterByAuthor(listItem) && filterByRecipient(listItem)
            && filterBySeenStatus(listItem)) {
            list.add(listItem)
            replyCountData.value = list.size
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