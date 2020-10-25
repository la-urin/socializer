package com.example.socializer.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
        title = "Groups"

        val recyclerView = findViewById<RecyclerView>(R.id.groups_recycler_view)
        val adapter = GroupAdapter(this) {
            val intent: Intent = Intent(this, GroupEditActivity::class.java)
            intent.putExtra("GroupId", it.id)
            startActivity(intent)
        }

        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(this)

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        groupViewModel.groups.observe(this, Observer { groups ->
            groups?.let { adapter.setGroups(it) }
        })

        val fab = findViewById<View>(R.id.groups_fab_add) as FloatingActionButton
        fab.setOnClickListener {
            val li: LayoutInflater = LayoutInflater.from(this)
            val promptsView: View = li.inflate(R.layout.alert_dialog_groupname, null)
            var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            var userInput: EditText = promptsView.findViewById(R.id.editTextDialogUserInput)

            alertDialogBuilder.setView(promptsView)
            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    val group = Group(userInput.text.toString())
                    groupViewModel.insert(group)

                    Toast.makeText(applicationContext, userInput.text, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(applicationContext, "I'm sick of groups", Toast.LENGTH_SHORT)
                        .show()
                }

            var alertDialog: AlertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }
    }
}