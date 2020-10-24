package com.example.socializer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.models.Group

class GroupAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context);
    private var groups = emptyList<Group>()

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupItemView: TextView = itemView.findViewById(R.id.group_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = inflater.inflate(R.layout.group_view_item, parent, false)
        return GroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val current: Group = groups[position]
        holder.groupItemView.text = current.name
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    internal fun setGroups(groups: List<Group>) {
        this.groups = groups;
    }
}
