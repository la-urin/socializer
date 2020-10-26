package com.example.socializer.models

import androidx.lifecycle.LiveData
import com.example.socializer.models.database.GroupDao

class GroupRepository(private val dao: GroupDao) {
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

    fun getGroupsSortedByAlphaAsc(): LiveData<List<Group>> {
        return dao.getGroupsSortedByAlphaAsc()
    }

    fun getGroupsSortedByAlphaDesc(): LiveData<List<Group>> {
        return dao.getGroupsSortedByAlphaDesc()
    }
}