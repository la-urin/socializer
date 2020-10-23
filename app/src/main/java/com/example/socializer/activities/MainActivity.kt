package com.example.socializer.activities

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socializer.R
import com.example.socializer.models.Group
import com.example.socializer.adapters.GroupAdapter

class MainActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvGroups = findViewById<RecyclerView>(R.id.groups_recycler_view)

        val group1 = listOf(
            Group("123"),
            Group("1234"),
            Group("17456234"),
            Group("12342234"),
            Group("145234"),
            Group("1676234"),
            Group("123466"),
            Group("12347"),
            Group("1234"),
            Group("1234"),
            Group("1234"),
            Group("1234"),
            Group("1234"),
            Group("1234"),
            Group("1234")
        );

        val groups: List<Group> = group1 //getGroups

        val adapter = GroupAdapter(groups)
        rvGroups.adapter = adapter
        rvGroups.layoutManager = LinearLayoutManager(this)

        adapter.onItemClick = { group ->
            val intent = Intent(this, GroupEditActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, group.name)
            }
            startActivity(intent)
            //Log.d("TAG", group.name)
        }
    }
}