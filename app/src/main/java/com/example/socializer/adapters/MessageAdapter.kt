package com.example.socializer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.models.Message

class MessageAdapter(
    context: Context,
    private val onItemEditClicked: (Message) -> Unit,
    private val onItemDeleteClicked: (Message) -> Unit
) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var messages = emptyList<Message>()

    inner class MessageViewHolder(
        itemView: View,
        onItemEditClicked: (Int) -> Unit,
        onItemDeleteClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        init {
            val editButton = itemView.findViewById<ImageButton>(R.id.message_view_item_button_edit)
            editButton.setOnClickListener { onItemEditClicked(adapterPosition) }
            val deleteButton =
                itemView.findViewById<ImageButton>(R.id.message_view_item_button_delete)
            deleteButton.setOnClickListener { onItemDeleteClicked(adapterPosition) }
        }

        val messageItemView: TextView = itemView.findViewById(R.id.message_view_item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = inflater.inflate(R.layout.message_view_item, parent, false)

        return MessageViewHolder(itemView,
            { onItemEditClicked(messages[it]) },
            { onItemDeleteClicked(messages[it]) })
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