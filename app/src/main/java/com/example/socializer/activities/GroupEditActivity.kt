package com.example.socializer.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.fragments.ContactsEditFragment
import com.example.socializer.fragments.GroupEditFragment
import com.example.socializer.fragments.MessagesEditFragment
import com.example.socializer.models.Group
import com.example.socializer.viewmodels.GroupViewModel
import com.google.android.material.tabs.TabLayout

private const val ARG_GROUP_ID = "GroupId"

class GroupEditActivity : AppCompatActivity() {
    private lateinit var messagesEditFragment: MessagesEditFragment
    private lateinit var contactsEditFragment: ContactsEditFragment
    private lateinit var groupEditFragment: GroupEditFragment
    private lateinit var currentFragment: Fragment
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_edit)

        val viewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        val groupId: Int = intent.getIntExtra("GroupId", 0)
        val group = viewModel.getById(groupId)
        title = group.name

        messagesEditFragment = MessagesEditFragment.newInstance(group.id)
        contactsEditFragment = ContactsEditFragment.newInstance(group.id)
        groupEditFragment = GroupEditFragment.newInstance(group.id)
        currentFragment = groupEditFragment

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        fragmentManager.beginTransaction().add(R.id.activity_group_edit_fragment, currentFragment)
            .commit()

        val tabLayout = findViewById<TabLayout>(R.id.activity_group_edit_tabs)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setActiveView(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                fragmentManager.beginTransaction().remove(currentFragment).commit()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                setActiveView(tab)
            }
        })
    }

    private fun setupEditGroupDialog() {
        var groupId = intent.getIntExtra("GroupId", 0)
        val groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        val group = groupViewModel.getById(groupId)
        val li: LayoutInflater = LayoutInflater.from(this)
        val promptsView: View = li.inflate(R.layout.alert_dialog_groupname, null)
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        var userInput: EditText = promptsView.findViewById(R.id.editTextDialogUserInput)

        userInput.setText(group.name)

        alertDialogBuilder.setView(promptsView)
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                group.name = userInput.text.toString()
                groupViewModel.update(group)
                title = group.name
                Toast.makeText(applicationContext, R.string.saved_successfully, Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                Toast.makeText(applicationContext, "Nope.", Toast.LENGTH_SHORT).show()
            }

        var alertDialog: AlertDialog = alertDialogBuilder.create();

        alertDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_button_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_name -> {
                setupEditGroupDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setActiveView(tab: TabLayout.Tab?) {
        currentFragment = when (tab?.position) {
            1 -> messagesEditFragment
            2 -> contactsEditFragment
            else -> groupEditFragment
        }

        fragmentManager.beginTransaction()
            .replace(R.id.activity_group_edit_fragment, currentFragment).commit()
    }
}