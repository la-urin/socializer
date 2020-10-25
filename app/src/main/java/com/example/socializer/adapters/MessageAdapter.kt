package com.example.socializer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.models.Message

class MessageAdapter internal constructor(context: Context) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var messages = emptyList<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageItemView: TextView = itemView.findViewById(R.id.group_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = inflater.inflate(R.layout.group_view_item, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val current: Message = messages[position]
        holder.messageItemView.text = current.text
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    internal fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }
}