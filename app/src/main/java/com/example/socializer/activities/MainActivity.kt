package com.example.socializer.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.adapters.GroupAdapter
import com.example.socializer.models.Group
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity() : AppCompatActivity() {
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Groups"

        val rvGroups = findViewById<RecyclerView>(R.id.groups_recycler_view)

        /*group1 = group1.sortedWith(Comparator { lhs, rhs ->
            if (lhs.name > rhs.name) 1 else -1
        })*/

        var groups: List<Group> = listOf(Group("Abc")) //groupViewModel.getAllGroups() //getGroups
        val adapter = GroupAdapter(groups)

        initView(rvGroups, adapter)

        val fab = findViewById<View>(R.id.groups_fab_add) as FloatingActionButton
        fab.setOnClickListener{
                var li: LayoutInflater = LayoutInflater.from(context)
                var promptsView: View = li.inflate(R.layout.alert_dialog_groupname, null)
                var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                var userInput: EditText = promptsView.findViewById(R.id.editTextDialogUserInput)

                alertDialogBuilder.setView(promptsView)
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK") { dialog, which ->
                            // TODO: save data to DB
                            Toast.makeText(applicationContext, userInput.text, Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Cancel") { dialog, which ->
                            Toast.makeText(applicationContext, "I'm sick of groups", Toast.LENGTH_SHORT).show()
                        }

                var alertDialog: AlertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }

            /*Snackbar.make(view!!, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
    }

    private fun initView(
        rvGroups: RecyclerView,
        adapter: GroupAdapter
    ) {
        rvGroups.adapter = adapter
        rvGroups.layoutManager = LinearLayoutManager(this)
        rvGroups.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        adapter.onItemClick = { group ->
            val intent = Intent(this, GroupEditActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, group.name)
            }
            startActivity(intent)
        }
    }
}