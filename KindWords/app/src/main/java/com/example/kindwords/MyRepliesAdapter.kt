package com.example.kindwords

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.io.Serializable
import kotlin.collections.ArrayList

class MyRepliesAdapter(mContext: Context, recipientFilter: String? = null,
                       authorFilter: String? = null) : BaseAdapter(), Serializable{

    private var list = ArrayList<Reply>()
    private var mRecipientFilter: String? = recipientFilter
    private var mAuthorFilter: String? = authorFilter

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
            
        }

        return newView
    }

    internal class ViewHolder {
        var subjectView: TextView? = null
        var dateView: TextView? = null
        var itemView: View? = null
    }


    private fun filterbyRecipient(listItem: Reply): Boolean {
        return mRecipientFilter == null || mRecipientFilter == listItem.recipientId
    }

    private fun filterByAuthor(listItem: Reply): Boolean {
        return mAuthorFilter == null || mAuthorFilter == listItem.authorId
    }


    fun add(listItem: Reply) {
        if (filterByAuthor(listItem) && filterbyRecipient(listItem)) {
            list.add(listItem)
            notifyDataSetChanged()
        }
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