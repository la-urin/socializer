package com.example.socializer.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.viewmodels.ContactViewModel
import com.example.socializer.viewmodels.GroupViewModel
import com.example.socializer.viewmodels.MessageViewModel
import com.google.android.material.snackbar.Snackbar


private const val ARG_GROUP_ID = "GroupId"

class GroupEditFragment : Fragment() {
    private var groupId: Int? = null
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var broadcastButton: Button
    private lateinit var fragment: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { groupId = it.getInt(ARG_GROUP_ID) }
        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        messageViewModel = ViewModelProvider(this)[MessageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragment = inflater.inflate(R.layout.group_edit_fragment, container, false)

        broadcastButton = fragment.findViewById(R.id.message_broadcast_button)
        broadcastButton.setOnClickListener {
            sendBroadCastMessage()
        }

        return fragment
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (grantResults.all { result -> result == PackageManager.PERMISSION_GRANTED }) {
            sendBroadCastMessage()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = GroupEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }

    private fun sendBroadCastMessage() {
        if (!checkPermissions()) {
            requestPermission()
        } else {
            val contacts = contactViewModel.getForGroup(groupId!!)
            var numbers = mutableSetOf<String>()
            val messages = messageViewModel.getForGroup(groupId!!)

            if (contacts.isNotEmpty() && messages.isNotEmpty()) {
                for (contact in contacts) {
                    val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID}=${contact.contactId}"
                    val numCursor = requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, null, null);
                    if (numCursor?.moveToFirst() == true) {
                        numbers.add(numCursor.getString(numCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                    }
                }

                var randomMessage: String = messages.random().text

                try {
                    val smsManager = SmsManager.getDefault()
                    for (number in numbers) {
                        smsManager.sendTextMessage(number, null, randomMessage, null, null)
                    }

                    val snackbar: Snackbar = Snackbar.make(fragment, "SMS sent!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("SHOW MESSAGES", View.OnClickListener() {
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
                        startActivity(intent)
                    }).show()
                } catch (e: Exception) {
                    Snackbar.make(fragment, "SMS sending failed", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace()
                }
            } else {
                Snackbar.make(fragment, "Please make sure to add contacts and messages first", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private fun requestPermission() {
        val permissions = arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS)
        requestPermissions(permissions, 0)
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }
}