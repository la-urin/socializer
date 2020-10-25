package com.example.socializer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.fragments.GroupEditContactFragment
import com.example.socializer.models.Group
import com.example.socializer.viewmodels.GroupViewModel

class GroupEditActivity : AppCompatActivity() {
    private lateinit var groupViewModel: GroupViewModel

    private lateinit var nameEditText: EditText
    private lateinit var messagesButton: Button
    private lateinit var contactsButton: Button
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_edit)

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        val groupId = intent.getIntExtra("GroupId", 0)
        if(groupId != 0)
        {
            var group = groupViewModel.getById(groupId)

            nameEditText = findViewById(R.id.group_edit_edittext_name)
            nameEditText.setText(group.name)

            saveButton = findViewById(R.id.group_edit_button_save)

            messagesButton = findViewById(R.id.group_edit_button_messages)

            contactsButton = findViewById(R.id.group_edit_button_contacts)
        }

        // Dynamic fragment insert
        // val mgr: FragmentManager = supportFragmentManager
        // val transaction = mgr.beginTransaction()
        // val fragment = GroupEditContactFragment()
        // transaction.add(R.id.group_edit_fragment_container, fragment)
        // transaction.commit()



        cancelButton = findViewById(R.id.group_edit_button_cancel)
    }
}