package com.example.socializer.adapters

import com.example.socializer.models.Group
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView

class GroupAdapter (private val mGroups: List<Group>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>()
{
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = listItemView.findViewById<TextView>(R.id.group_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val groupView = inflater.inflate(R.layout.group_view_item, parent, false)

        return ViewHolder(groupView)
    }

    override fun onBindViewHolder(viewHolder: GroupAdapter.ViewHolder, position: Int) {
        val group: Group = mGroups.get(position)
        val textView = viewHolder.nameTextView
        textView.setText(group.name)
    }

    override fun getItemCount(): Int {
        return mGroups.size
    }
}