package com.example.socializer.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.socializer.enums.Sort
import com.example.socializer.models.Group
import com.example.socializer.models.GroupRepository
import com.example.socializer.models.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GroupViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository
    private val currentSort: MutableLiveData<Sort> = MutableLiveData(Sort.ALPHA_ASC)
    var groups: LiveData<List<Group>>

    init {
        val dao = AppDatabase.getDatabase(application).groupDao()
        repository = GroupRepository(dao)
        groups = currentSort.switchMap { value ->
            if (value == Sort.ALPHA_DES) {
                return@switchMap repository.getGroupsSortedByAlphaDesc()
            }

            return@switchMap repository.getGroupsSortedByAlphaAsc()
        }
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

    fun toggleSortMode() = viewModelScope.launch(Dispatchers.IO) {
        if (currentSort.value == Sort.ALPHA_DES) {
            currentSort.postValue(Sort.ALPHA_ASC)
        } else {
            currentSort.postValue(Sort.ALPHA_DES)
        }
    }
}