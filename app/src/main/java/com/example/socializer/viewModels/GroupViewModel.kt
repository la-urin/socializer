package com.example.socializer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.socializer.database.AppDatabase
import com.example.socializer.database.GroupDao
import com.example.socializer.models.Group

class GroupViewModel(application: Application): AndroidViewModel(application) {
    private val groupDao: GroupDao = AppDatabase.getDatabase(application).groupDao()
    private lateinit var group: Group

    fun getAllGroups(): List<Group> {
        return groupDao.getAll()
    }

    fun insertGroup(name: String) {
        group = Group(name)
        groupDao.insert(group)
    }
}
