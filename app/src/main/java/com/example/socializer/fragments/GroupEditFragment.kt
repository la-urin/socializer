package com.example.socializer.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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

        requestPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragment = inflater.inflate(R.layout.group_edit_fragment, container, false)

        broadcastButton = fragment.findViewById(R.id.message_broadcast_button)
        broadcastButton.setOnClickListener {
            val contacts = contactViewModel.getForGroup(groupId!!)
            var numbers = mutableListOf<String>()

            if (contacts.isNotEmpty()) {

                for (contact in contacts) {
                    val lookupUri: Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, contact.lookupKey)
                    val res: Uri = ContactsContract.Contacts.lookupContact(context?.contentResolver, lookupUri)

                    val cursor = requireActivity().contentResolver.query(res, null, null, null, null)
                    if (cursor?.moveToFirst() == true) {
                        val hasPhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                        if (Integer.valueOf(hasPhoneNumber) == 1) {
                            val numCursor = requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                            while (numCursor?.moveToNext()!!) {
                                numbers.add(numCursor.getString(numCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                            }
                        }
                    }
                }
            } else {
                Snackbar.make(fragment, "Please make sure to add a contact first.", Snackbar.LENGTH_LONG).show();
            }

            val messages = messageViewModel.getForGroup(groupId!!)

            if (messages.isNotEmpty()) {
                var randomMessage: String = messages.random().text

                try {
                    val smsManager = SmsManager.getDefault()
                    for (number in numbers) {
                        smsManager.sendTextMessage(number, null, randomMessage, null, null)
                    }
                    Toast.makeText(context, "SMS Sent!", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "SMS failed, please try again later!", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                Snackbar.make(fragment, "Please make sure to add a contact first.", Snackbar.LENGTH_LONG).show();
            }
        }

        return fragment
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = GroupEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }

    private fun requestPermission() {
        requestPermission(Manifest.permission.SEND_SMS, 0)
        requestPermission(Manifest.permission.READ_CONTACTS, 0)
    }

    private fun checkPermission(permission: String): Boolean {
        return context?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermission(grantResults: IntArray): Boolean {
        return if (grantResults.isEmpty()) {
            false
        } else {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }

    @SuppressLint("NewApi")
    private fun requestPermission(permission: String, requestCode: Int) {
        if (checkPermission(permission)) return
        requestPermissions(arrayOf(permission), requestCode)
    }
}