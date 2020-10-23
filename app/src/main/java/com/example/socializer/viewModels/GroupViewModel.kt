package com.example.socializer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.socializer.database.AppDatabase
import com.example.socializer.database.GroupDao
import com.example.socializer.models.Group

class GroupViewModel(application: Application): AndroidViewModel(application) {
    private val groupDao: GroupDao = AppDatabase.getDatabase(application).groupDao()
    private val groupList: List<Group>

    init {
        groupList = groupDao.getAll()
    }

    fun getAllGroups(): List<Group> {
        return groupList
    }

}