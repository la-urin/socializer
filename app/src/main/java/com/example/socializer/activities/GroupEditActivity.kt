package com.example.socializer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.fragments.ContactsEditFragment
import com.example.socializer.fragments.GroupEditFragment
import com.example.socializer.fragments.MessagesEditFragment
import com.example.socializer.viewmodels.GroupViewModel
import com.google.android.material.tabs.TabLayout

class GroupEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_edit)

        val fragmentManager = supportFragmentManager;


        val tabLayout = findViewById<TabLayout>(R.id.activity_group_edit_tabs)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    1 -> fragmentManager.beginTransaction().replace(R.id.activity_group_edit_fragment, MessagesEditFragment()).commit()
                    2 -> fragmentManager.beginTransaction().replace(R.id.activity_group_edit_fragment, ContactsEditFragment()).commit()
                    else -> fragmentManager.beginTransaction().replace(R.id.activity_group_edit_fragment, GroupEditFragment()).commit()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }
}