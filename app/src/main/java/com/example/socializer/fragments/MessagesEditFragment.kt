package com.example.socializer.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.adapters.MessageAdapter
import com.example.socializer.viewmodels.MessageViewModel

class MessagesEditFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MessagesEditFragment()
    }

    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.messages_edit_fragment, container, false)

        val adapter = context?.let { MessageAdapter(it) }

        recyclerView = fragment.findViewById(R.id.messages_edit_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.messages.observe(
            viewLifecycleOwner,
            Observer { messages -> messages?.let {
                adapter?.setMessages(messages)
            } })
        return fragment
    }

}