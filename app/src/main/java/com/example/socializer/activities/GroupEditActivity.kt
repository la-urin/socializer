package com.example.socializer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.socializer.R
import com.example.socializer.fragments.ContactsEditFragment
import com.example.socializer.fragments.GroupEditFragment
import com.example.socializer.fragments.MessagesEditFragment
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

        val groupId = intent.getIntExtra("GroupId", 0)
        val bundle = Bundle()
        bundle.putInt("GroupId", groupId)
        messagesEditFragment.arguments = bundle
        contactsEditFragment.arguments = bundle
        groupEditFragment.arguments = bundle

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
            .replace(R.id.activity_group_edit_fragment, currentFragment).commit()
    }
}