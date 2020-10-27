package com.example.socializer.models

import androidx.lifecycle.LiveData
import com.example.socializer.models.database.ContactDao
import com.example.socializer.models.database.GroupDao
import com.example.socializer.models.database.MessageDao

class GroupRepository(private val dao: GroupDao, private val messageDao: MessageDao, private val contactDao: ContactDao) {
    fun insert(group: Group) {
        dao.insert(group)
    }

    fun  update(group: Group){
        dao.update(group)
    }

    fun delete(group: Group){
        dao.delete(group)
        messageDao.deleteForGroup(group.id)
        contactDao.deleteForGroup(group.id)
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