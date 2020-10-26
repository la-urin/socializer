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
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.messages_edit_fragment, container, false)

        setupAddMessageDialog(fragment)
        setupMessageRecyclerView(fragment)

        return fragment
    }

    private fun setupMessageRecyclerView(fragment: View) {
        val adapter = context?.let {
            MessageAdapter(it,
                { message -> setupEditMessageDialog(message) },
                { message -> viewModel.delete(message) })
        }

        viewModel.getForGroup(groupId).observe(viewLifecycleOwner)
        { messages -> messages?.let { adapter?.setMessages(messages) } }

        recyclerView = fragment.findViewById(R.id.messages_edit_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupAddMessageDialog(fragment: View) {
        val button = fragment.findViewById<FloatingActionButton>(R.id.messages_edit_fragment_add)
        val inflater = LayoutInflater.from(context)
        val builder = AlertDialog.Builder(context)

        button.setOnClickListener {
            val popupView = inflater.inflate(R.layout.message_edit_dialog, null)
            val userInput: EditText = popupView.findViewById(R.id.message_edit_dialog_edit_text)

            builder.setView(popupView).setCancelable(true)
                .setPositiveButton(R.string.ok) { _, _ ->
                    val message = Message(groupId, userInput.text.toString())
                    viewModel.insert(message)
                    Toast.makeText(context, R.string.saved_successfully, Toast.LENGTH_SHORT).show()
                }.setNegativeButton(R.string.cancel, null).create().show()
        }
    }

    private fun setupEditMessageDialog(message: Message) {
        val inflater = LayoutInflater.from(context)
        val builder = AlertDialog.Builder(context)

        val popupView = inflater.inflate(R.layout.message_edit_dialog, null)
        val userInput: EditText = popupView.findViewById(R.id.message_edit_dialog_edit_text)

        userInput.setText(message.text)
        builder.setView(popupView).setCancelable(true)
            .setPositiveButton(R.string.ok) { _, _ ->
                message.text = userInput.text.toString()
                viewModel.update(message)
                Toast.makeText(context, R.string.saved_successfully, Toast.LENGTH_SHORT).show()
            }.setNegativeButton(R.string.cancel, null).create().show()
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = MessagesEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }
}