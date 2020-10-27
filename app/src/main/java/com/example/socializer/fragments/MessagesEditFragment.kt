package com.example.socializer.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.adapters.MessageAdapter
import com.example.socializer.models.Message
import com.example.socializer.viewmodels.MessageViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_GROUP_ID = "GroupId"

class MessagesEditFragment : Fragment() {
    private var groupId: Int = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { groupId = it.getInt(ARG_GROUP_ID) }
        viewModel = ViewModelProvider(this)[MessageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.messages_edit_fragment, container, false)

        setupAddMessageDialog(fragment)
        setupMessageRecyclerView(fragment)

        return fragment
    }

    private fun setupMessageRecyclerView(fragment: View) {
        val adapter = context?.let {
            MessageAdapter(it,
                { message -> openEditMessageDialog(message) { message -> updateMessage(message) } },
                { message -> viewModel.delete(message) })
        }

        viewModel.getForGroupLive(groupId).observe(viewLifecycleOwner)
        { messages -> messages?.let { adapter?.setMessages(messages) } }

        recyclerView = fragment.findViewById(R.id.messages_edit_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupAddMessageDialog(fragment: View) {
        val button = fragment.findViewById<FloatingActionButton>(R.id.messages_edit_fragment_add)
        button.setOnClickListener {
            openEditMessageDialog(null) { message ->
                insertMessage(message)
            }
        }
    }

    private fun openEditMessageDialog(message: Message?, onSaveMessage: (message: Message) -> Unit) {
        val inflater = LayoutInflater.from(context)
        val builder = AlertDialog.Builder(context)

        val popupView = inflater.inflate(R.layout.message_edit_dialog, null)
        val messageInput: EditText = popupView.findViewById(R.id.message_edit_dialog_edit_text)

        if (message != null) {
            messageInput.setText(message.text)
        }

        val alertDialog: AlertDialog = builder.setView(popupView).setCancelable(true)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()

        alertDialog.setOnShowListener {
            val button: Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val messageText = messageInput.text.toString()
                if (messageText.isNotEmpty()) {
                    if (message == null) {
                        onSaveMessage(Message(groupId, messageText))
                    } else {
                        message.text = messageText
                        onSaveMessage(message)
                    }
                    alertDialog.dismiss()
                } else {
                    messageInput.error = getString(R.string.messageTextRequired)
                }
            }
        }

        alertDialog.show()
    }

    private fun insertMessage(message: Message) {
        viewModel.insert(message)
        Toast.makeText(context, R.string.saved_successfully, Toast.LENGTH_SHORT).show()
    }

    private fun updateMessage(message: Message) {
        viewModel.update(message)
        Toast.makeText(context, R.string.saved_successfully, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = MessagesEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }
}