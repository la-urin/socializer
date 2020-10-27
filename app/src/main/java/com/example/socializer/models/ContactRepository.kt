package com.example.socializer.models

import androidx.lifecycle.LiveData
import com.example.socializer.models.database.ContactDao

class ContactRepository(private val dao: ContactDao) {
    fun getForGroupLive(groupId: Int): LiveData<List<Contact>> {
        return dao.getForGroupLive(groupId)
    }

    fun getForGroup(groupId: Int): List<Contact> {
        return dao.getForGroup(groupId)
    }

    fun getForGroupByExternalContactId(groupId: Int, contactId: Long) : Contact? {
        return dao.getForGroupByExternalContactId(groupId, contactId)
    }

    fun insert(contact: Contact) {
        dao.insert(contact)
    }

    fun delete(contact: Contact){
        dao.delete(contact)
    }
}