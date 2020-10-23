package com.example.socializer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.models.Group

class GroupAdapter (private val mGroups: List<Group>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>()
{
    var onItemClick: ((Group) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val groupView = inflater.inflate(R.layout.group_view_item, parent, false)

        return ViewHolder(groupView)
    }

    override fun onBindViewHolder(viewHolder: GroupAdapter.ViewHolder, position: Int) {
        val group: Group = mGroups[position]
        val textView = viewHolder.nameTextView
        textView.text = group.name
    }

    override fun getItemCount(): Int {
        return mGroups.size
    }

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView: TextView = listItemView.findViewById<TextView>(R.id.group_name)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(mGroups[adapterPosition])
            }
        }
    }
}
