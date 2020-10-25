package com.example.socializer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.fragments.ContactsEditFragment
import com.example.socializer.fragments.GroupEditFragment
import com.example.socializer.fragments.MessagesEditFragment
import com.example.socializer.viewmodels.GroupViewModel
import com.google.android.material.tabs.TabLayout

class GroupEditActivity : AppCompatActivity() {
    private val messagesEditFragment = MessagesEditFragment()
    private val contactsEditFragment = ContactsEditFragment()
    private val groupEditFragment = GroupEditFragment()

    private var currentFragment: Fragment = groupEditFragment;
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_edit)

        fragmentManager.beginTransaction()
            .add(R.id.activity_group_edit_fragment, currentFragment)
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

    private fun setActiveView(tab: TabLayout.Tab?) {
        currentFragment = when (tab?.position) {
            1 -> messagesEditFragment
            2 -> contactsEditFragment
            else -> groupEditFragment
        }

        fragmentManager.beginTransaction()
            .add(R.id.activity_group_edit_fragment, currentFragment).commit()
    }
}