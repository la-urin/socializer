package com.example.socializer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.viewmodels.GroupViewModel

private const val ARG_PARAM_GROUP_ID = "GroupId"

class GroupEditFragment : Fragment() {
    private var groupId: Int? = null
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var nameEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupId = it.getInt(ARG_PARAM_GROUP_ID)
        }

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val group = groupViewModel.getById(groupId!!)

        val fragment = inflater.inflate(R.layout.group_edit_fragment, container, false)
        nameEditText = fragment.findViewById(R.id.group_edit_fragment_edit_name)
        nameEditText.setText(group.name)

        saveButton = fragment.findViewById(R.id.group_edit_fragment_button_save)
        saveButton.setOnClickListener {
            group.name = nameEditText.text.toString()
            groupViewModel.update(group)
            Toast.makeText(context, R.string.saved_successfully, Toast.LENGTH_SHORT).show()
        }

        return fragment
    }
}