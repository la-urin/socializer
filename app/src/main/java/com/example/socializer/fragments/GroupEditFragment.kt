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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.socializer.R
import com.example.socializer.models.Contact
import com.example.socializer.viewmodels.ContactViewModel
import com.example.socializer.viewmodels.GroupViewModel
import com.example.socializer.viewmodels.MessageViewModel
import com.google.android.material.snackbar.Snackbar


private const val ARG_GROUP_ID = "GroupId"
private const val BROADCAST_PERMISSION_ID = 99
private const val RANDOM_CALL_PERMISSION_ID = 98


class GroupEditFragment : Fragment() {
    private var groupId: Int? = null
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var broadcastButton: Button
    private lateinit var randomCallButton: Button
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

        randomCallButton = fragment.findViewById(R.id.random_call_button)
        randomCallButton.setOnClickListener {
            makeRandomCall()
        }

        return fragment
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = GroupEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }

    private fun sendBroadCastMessage() {
        if (!checkPermissionsBroadcast()) {
            requestPermissionBroadcast(BROADCAST_PERMISSION_ID)
        } else {
            val contacts = contactViewModel.getForGroup(groupId!!)
            var numbers = mutableSetOf<String>()
            val messages = messageViewModel.getForGroup(groupId!!)

            if (contacts.isNotEmpty() && messages.isNotEmpty()) {
                for (contact in contacts) {
                    val number = getNumberFromContact(contact)
                    if (number != null) {
                        numbers.add(number)
                    }
                }

                var randomMessage: String = messages.random().text

                try {
                    val smsManager = SmsManager.getDefault()
                    for (number in numbers) {
                        smsManager.sendTextMessage(number, null, randomMessage, null, null)
                    }

                    val snackbar: Snackbar = Snackbar.make(fragment, R.string.messageSent, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.showMessages, View.OnClickListener() {
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
                        startActivity(intent)
                    }).show()
                } catch (e: Exception) {
                    Snackbar.make(fragment, R.string.messageNotSent, Snackbar.LENGTH_LONG).show();
                    e.printStackTrace()
                }
            } else {
                Snackbar.make(fragment, R.string.messageNotSent, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private fun makeRandomCall() {
        if (!checkPermissionsCall()) {
            requestPermissionCall(RANDOM_CALL_PERMISSION_ID)
        } else {
            val contacts = contactViewModel.getForGroup(groupId!!)
            var numbers = mutableSetOf<String>()

            if (contacts.isNotEmpty()) {
                for (contact in contacts) {
                    getNumberFromContact(contact)?.let { numbers.add(it) }
                }

                var randomNumber = numbers.random()

                val uri = "tel:" + randomNumber.trim()
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            } else {
                Snackbar.make(fragment, R.string.call_not_made, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private fun getNumberFromContact(contact: Contact): String? {
        val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID}=${contact.contactId}"
        val numCursor = requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, null, null);
        if (numCursor?.moveToFirst() == true) {
            return numCursor.getString(numCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        }
        return null
    }

    private fun requestPermissionBroadcast(requestCode: Int) {
        val permissions = arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS)
        requestPermissions(permissions, requestCode)
    }

    private fun requestPermissionCall(requestCode: Int) {
        val permissions = arrayOf(Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS)
        requestPermissions(permissions, requestCode)
    }

    private fun checkPermissionsBroadcast(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionsCall(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.all { result -> result == PackageManager.PERMISSION_GRANTED }) {
            when (requestCode) {
                BROADCAST_PERMISSION_ID -> sendBroadCastMessage()
                RANDOM_CALL_PERMISSION_ID -> print("todo") // random call todo
                else -> print("unsupported request code $requestCode")
            }
        } else {
            Toast.makeText(requireContext(), R.string.messageNotSentPermission, Toast.LENGTH_LONG)
                .show()
        }
    }
}
