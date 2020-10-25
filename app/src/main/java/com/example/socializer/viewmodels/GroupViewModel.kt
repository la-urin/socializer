package com.example.socializer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socializer.enums.Sort
import com.example.socializer.models.Group
import com.example.socializer.models.GroupRepository
import com.example.socializer.models.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository
    var groups: MutableLiveData<List<Group>>
    var currentSort: Sort = Sort.ALPHA_ASC

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

    fun sortBy(sortMethod: Sort) = viewModelScope.launch(Dispatchers.IO) {
        when (sortMethod) {
            Sort.ALPHA_ASC -> {
                repository.sortBy(Sort.ALPHA_ASC)
                currentSort = Sort.ALPHA_DES
            }
            Sort.ALPHA_DES -> {
                repository.sortBy(Sort.ALPHA_DES)
                currentSort = Sort.ALPHA_ASC
            }
        }
    }
}