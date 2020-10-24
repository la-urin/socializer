package com.example.socializer.models

import androidx.lifecycle.LiveData
import com.example.socializer.models.database.GroupDao

class GroupRepository(private val dao: GroupDao) {
    val groups: LiveData<List<Group>> = dao.getAll()

    fun insert(group: Group){
        dao.insert(group)
    }
}