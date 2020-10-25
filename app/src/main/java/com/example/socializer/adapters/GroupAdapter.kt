package com.example.socializer.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.activities.GroupEditActivity
import com.example.socializer.models.Group
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

class GroupAdapter(context: Context, private val onItemClicked: (Group) -> Unit) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context);
    private var groups = emptyList<Group>()

    inner class GroupViewHolder(itemView: View, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onItemClicked(adapterPosition) }
        }

        val groupItemView: TextView = itemView.findViewById(R.id.group_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = inflater.inflate(R.layout.group_view_item, parent, false)
        return GroupViewHolder(itemView) {
            onItemClicked(groups[it])
        }
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
