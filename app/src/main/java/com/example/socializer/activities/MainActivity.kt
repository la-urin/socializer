package com.example.socializer.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
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
        title = "Socializer - Groups"

        setupGroupRecyclerView()
        setupAddGroupDialog()
        setupSortFunction()
    }

    override fun onResume() {
        super.onResume()

        setupGroupRecyclerView()
        setupAddGroupDialog()
        setupSortFunction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                this.setTheme(R.style.DrunkMode)
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

        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
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
                    .setPositiveButton(R.string.ok) { _, _ ->
                        val group = Group(userInput.text.toString())
                        groupViewModel.insert(group)

                        Toast.makeText(applicationContext, String.format("Group %s added", userInput.text), Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(R.string.cancel) { _, _ ->
                        Toast.makeText(applicationContext, "Nope.", Toast.LENGTH_SHORT)
                                .show()
                    }

            var alertDialog: AlertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }
    }

    private fun setupSortFunction() {
        val fab = findViewById<View>(R.id.groups_fab_sort) as FloatingActionButton
        fab.setOnClickListener {
            groupViewModel.toggleSortMode()
        }
    }

}