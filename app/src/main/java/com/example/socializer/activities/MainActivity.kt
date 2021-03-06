package com.example.socializer.activities

import android.app.AlertDialog
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.adapters.GroupAdapter
import com.example.socializer.models.Group
import com.example.socializer.viewmodels.GroupViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity() : AppCompatActivity() {
    private lateinit var groupViewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)

        setupGroupRecyclerView()
        setupAddGroupDialog()
    }

    override fun onResume() {
        super.onResume()

        setupGroupRecyclerView()
        setupAddGroupDialog()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_button_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_group -> {
                groupViewModel.toggleSortMode()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupGroupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.groups_recycler_view)
        val adapter = GroupAdapter(this) {
            val intent: Intent = Intent(this, GroupEditActivity::class.java)
            intent.putExtra("GroupId", it.id)
            startActivity(intent)
        }


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        groupViewModel.groups.observe(this, Observer { groups ->
            groups?.let { adapter.setGroups(it) }
        })
    }

    private fun setupAddGroupDialog() {
        val fab = findViewById<View>(R.id.groups_fab_add) as FloatingActionButton
        fab.setOnClickListener {
            val li: LayoutInflater = LayoutInflater.from(this)
            val promptsView: View = li.inflate(R.layout.alert_dialog_groupname, null)
            var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            var userInput: EditText = promptsView.findViewById(R.id.editTextDialogUserInput)

            alertDialogBuilder.setView(promptsView)
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, null)
                    .setNegativeButton(R.string.cancel) { _, _ ->
                        Toast.makeText(applicationContext, R.string.nope, Toast.LENGTH_SHORT).show()
                    }

            var alertDialog: AlertDialog = alertDialogBuilder.create();

            alertDialog.setOnShowListener(OnShowListener {
                val button: Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener(View.OnClickListener {
                    val groupName = userInput.text.toString()
                    if (groupName.isNotEmpty()) {
                        insertGroup(groupName)
                        alertDialog.dismiss()
                    } else {
                        userInput.error = getString(R.string.groupNameRequired)
                    }
                })
            })

            alertDialog.show();

            userInput.setOnEditorActionListener { view, actionId, _ ->
                val groupName = view.text.toString()
                if (groupName.isNotEmpty() && actionId == EditorInfo.IME_ACTION_DONE) {
                    alertDialog.dismiss()
                    insertGroup(groupName.toString())
                    return@setOnEditorActionListener true;
                }

                return@setOnEditorActionListener false;
            }
        }
    }

    private fun insertGroup(name: String) {
        val group = Group(name)
        groupViewModel.insert(group)
        Toast.makeText(applicationContext, String.format(getString(R.string.group_added), group.name), Toast.LENGTH_SHORT).show()
    }

}