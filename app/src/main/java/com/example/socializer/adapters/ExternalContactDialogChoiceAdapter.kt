package com.example.socializer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.socializer.models.ExternalContact

class ExternalContactDialogChoiceAdapter(context: Context, contacts: List<ExternalContact>) : ArrayAdapter<ExternalContact>(context, android.R.layout.select_dialog_item, contacts) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val contact = getItem(position);
        if (contact != null) {
            val text1 = view.findViewById<TextView>(android.R.id.text1);
            text1.text = contact.displayName
        }

        return view;
    }
}