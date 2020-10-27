package com.example.socializer.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.socializer.R
import com.example.socializer.viewmodels.ContactViewModel
import com.example.socializer.viewmodels.GroupViewModel

private const val ARG_GROUP_ID = "GroupId"

class GroupEditFragment : Fragment() {
    private var groupId: Int? = null
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var nameEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var randomCallButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { groupId = it.getInt(ARG_GROUP_ID) }
        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val group = groupViewModel.getById(groupId!!)
        val fragment = inflater.inflate(R.layout.group_edit_fragment, container, false)

        randomCallButton = fragment.findViewById(R.id.group_edit_fragment_button_random_call)
        randomCallButton.setOnClickListener(randomCall())

        return fragment
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = GroupEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }

    private fun randomCall(): (View) -> Unit {
        return {
            if (getContext()?.let { it1 ->
                    ActivityCompat.checkSelfPermission(
                        it1,
                        Manifest.permission.CALL_PHONE
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
            } else {
                contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
                contactViewModel.getForGroup(groupId!!).observe(
                    viewLifecycleOwner
                ) { contacts ->
                    if (contacts.isNotEmpty()) {
                        val lookupKey = contacts.random().lookupKey
                        val lookupUri = Uri.withAppendedPath(
                            ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                            lookupKey
                        )
                        val contract = ContactsContract.Contacts.lookupContact(activity?.contentResolver, lookupUri)



                        val number = "0797836079"

                        val uri = "tel:" + number.trim()
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse(uri)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, R.string.no_contact_found, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (permissions[0] == Manifest.permission.CALL_PHONE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                randomCall()
            }
        }
    }

}