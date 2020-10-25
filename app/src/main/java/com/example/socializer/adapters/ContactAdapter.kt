package com.example.socializer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.models.Contact

class ContactAdapter(
    context: Context,
    private val onItemDeleteClicked: (Contact) -> Unit
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var contacts = emptyList<Contact>()

    inner class ContactViewHolder(
        itemView: View,
        onItemDeleteClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        init {
            val deleteButton =
                itemView.findViewById<ImageButton>(R.id.contact_view_item_button_delete)
            deleteButton.setOnClickListener { onItemDeleteClicked(adapterPosition) }
        }

        val contactItemView: TextView = itemView.findViewById(R.id.contact_view_item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = inflater.inflate(R.layout.contact_view_item, parent, false)

        return ContactViewHolder(itemView) { onItemDeleteClicked(contacts[it]) }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current: Contact = contacts[position]
        holder.contactItemView.text = current.displayName
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    internal fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}