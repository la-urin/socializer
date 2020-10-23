package com.example.socializer.activities

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socializer.R
import com.example.socializer.models.Group
import com.example.socializer.adapters.GroupAdapter
import com.example.socializer.viewModels.GroupViewModel

class MainActivity() : AppCompatActivity() {
    private lateinit var groupViewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvGroups = findViewById<RecyclerView>(R.id.groups_recycler_view)

        /*group1 = group1.sortedWith(Comparator { lhs, rhs ->
            if (lhs.name > rhs.name) 1 else -1
        })*/

        var groups: List<Group> = emptyList()//groupViewModel.getAllGroups() //getGroups
        val adapter = GroupAdapter(groups)

        initView(rvGroups, adapter)
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