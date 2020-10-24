package com.example.socializer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.socializer.models.Group
import com.example.socializer.models.GroupRepository
import com.example.socializer.models.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository
    val groups: LiveData<List<Group>>

    init {
        val dao = AppDatabase.getDatabase(application).groupDao()
        repository = GroupRepository(dao)
        groups = repository.groups
    }

    fun insert(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(group)
    }

    fun update(group: Group) = viewModelScope.launch {
        repository.update(group)
    }

    fun delete(group: Group) = viewModelScope.launch {
        repository.delete(group)
    }

    fun getById(id: Int): Group {
        return repository.getById(id);
    }
}