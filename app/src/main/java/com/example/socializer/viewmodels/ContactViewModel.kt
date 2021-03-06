package com.example.socializer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.socializer.models.Contact
import com.example.socializer.models.ContactRepository
import com.example.socializer.models.database.AppDatabase
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository

    init {
        val dao = AppDatabase.getDatabase(application).contactDao()
        repository = ContactRepository(dao)
    }

    fun getForGroupLive(groupId: Int): LiveData<List<Contact>> {
        return repository.getForGroupLive(groupId);
    }

    fun getForGroup(groupId: Int): List<Contact> {
        return repository.getForGroup(groupId);
    }

    fun contactAlreadyAdded(groupId: Int, externalContactId: Long): Boolean {
        return repository.getForGroupByExternalContactId(groupId, externalContactId) != null
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
    }
}