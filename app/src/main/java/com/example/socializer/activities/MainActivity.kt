package com.example.socializer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socializer.R
import com.example.socializer.models.Group
import com.example.socializer.adapters.GroupAdapter

class MainActivity(private var groups: List<Group>) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvGroups = findViewById(R.id.groups_recycler_view) as RecyclerView

        groups = emptyList() //getGroups

        val adapter = GroupAdapter(groups)
        rvGroups.adapter = adapter
        rvGroups.layoutManager = LinearLayoutManager(this)
    }
}