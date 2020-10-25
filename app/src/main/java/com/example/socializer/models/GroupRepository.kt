package com.example.socializer.models

import androidx.lifecycle.MutableLiveData
import com.example.socializer.enums.Sort
import com.example.socializer.models.database.GroupDao

class GroupRepository(private val dao: GroupDao) {
    val groups = MutableLiveData<List<Group>>()
    fun getAllDecks(): MutableLiveData<List<Group>> = groups

    fun insert(group: Group) {
        dao.insert(group)
    }

    fun  update(group: Group){
        dao.update(group)
    }

    fun delete(group: Group){
        dao.delete(group)
    }

    fun getById(id: Int): Group {
        return dao.getById(id);
    }

    fun sortBy(sortingMethod: Sort) {
        when (sortingMethod) {
            Sort.ALPHA_ASC -> groups.postValue(dao.getGroupsSortedByAlphaAsc())
            Sort.ALPHA_DES -> groups.postValue(dao.getGroupsSortedByAlphaDesc())
        }
    }
}