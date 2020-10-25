package com.example.socializer.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.adapters.MessageAdapter
import com.example.socializer.models.Message
import com.example.socializer.viewmodels.MessageViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MessagesEditFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.messages_edit_fragment, container, false)

        val fab = fragment.findViewById<FloatingActionButton>(R.id.messages_edit_fragment_add)
        fab.setOnClickListener {
            val popupView = inflater.inflate(R.layout.alert_dialog_groupname, null)
            var builder = AlertDialog.Builder(context)
            var userInput: EditText = popupView.findViewById(R.id.editTextDialogUserInput)

            builder.setView(popupView).setCancelable(true)
                .setPositiveButton(R.string.ok) { _, _ ->
                    val message = Message(userInput.text.toString())
                    viewModel.insert(message)
                    Toast.makeText(context, R.string.saved_successfully, Toast.LENGTH_SHORT).show()
                }.setNegativeButton(R.string.cancel, null).create().show()
        }

        val adapter = context?.let { MessageAdapter(it, {}, {}) }

        recyclerView = fragment.findViewById(R.id.messages_edit_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.messages.observe(
            viewLifecycleOwner,
            Observer { messages ->
                messages?.let {
                    adapter?.setMessages(messages)
                }
            })
        return fragment
    }
}