package com.example.socializer.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socializer.R
import com.example.socializer.adapters.ContactAdapter
import com.example.socializer.models.Contact
import com.example.socializer.viewmodels.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_GROUP_ID = "GroupId"
private const val CALLBACK_CODE = 1
const val PICK_CONTACT = 2015

class ContactsEditFragment : Fragment() {
    private var groupId: Int = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var viewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { groupId = it.getInt(ARG_GROUP_ID) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.contacts_edit_fragment, container, false)

        val adapter = context?.let { ContactAdapter(it) { contact -> viewModel.delete(contact) } }

        recyclerView = fragment.findViewById(R.id.contacts_edit_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        viewModel.getForGroupLive(groupId).observe(
            viewLifecycleOwner,
            { contacts ->
                contacts?.let {
                    adapter?.setContacts(contacts)
                }
            })

        addButton = fragment.findViewById(R.id.contacts_edit_add)
        addButton.setOnClickListener {
            pickExternalContact()
        }

        return fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            val cursor = data!!.data?.let {
                requireActivity().contentResolver.query(
                    it,
                    null,
                    null,
                    null,
                    null
                )
            }
            if (cursor?.moveToFirst() == true) {
                val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val lookupKey =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (viewModel.contactAlreadyAdded(contactId)) {
                    Toast.makeText(
                        requireContext(),
                        R.string.contactAlreadyAdded,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewModel.insert(Contact(0, groupId, contactId, lookupKey, displayName))
                }
            } else {
                showUnableToAddContactToast()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickExternalContact()
        } else {
            showUnableToAddContactToast()
        }
    }

    private fun pickExternalContact() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), CALLBACK_CODE)
        } else {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT)
        }
    }

    private fun showUnableToAddContactToast() {
        Toast.makeText(
                requireContext(),
                R.string.unableToAddContact,
                Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) = ContactsEditFragment().apply {
            arguments = Bundle().apply { putInt(ARG_GROUP_ID, groupId) }
        }
    }
}