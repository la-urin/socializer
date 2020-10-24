package com.example.socializer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.example.socializer.R
import com.example.socializer.fragments.GroupEditContactFragment
import com.example.socializer.models.Group

class GroupEditActivity: AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var messagesButton: Button
    private lateinit var contactsButton: Button
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_edit)

        val group = Group("Leos drunken gang.")

        // Dynamic fragment insert
        val mgr: FragmentManager = supportFragmentManager
        val transaction = mgr.beginTransaction()
        val fragment = GroupEditContactFragment()
        transaction.add(R.id.group_edit_fragment_container, fragment)
        transaction.commit()

        nameEditText = findViewById(R.id.group_edit_edittext_name)
        nameEditText.setText(group.name)

        messagesButton = findViewById(R.id.group_edit_button_messages)

        contactsButton = findViewById(R.id.group_edit_button_contacts)

        saveButton = findViewById(R.id.group_edit_button_save)

        cancelButton = findViewById(R.id.group_edit_button_cancel)
    }
}