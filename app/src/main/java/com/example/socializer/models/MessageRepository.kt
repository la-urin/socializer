package com.example.socializer.models

import androidx.lifecycle.LiveData
import com.example.socializer.models.database.MessageDao

class MessageRepository(private val dao: MessageDao) {
    fun getForGroup(groupId: Int): LiveData<List<Message>>{
        return dao.getForGroup(groupId)
    }

    fun insert(message: Message){
        dao.insert(message)
    }

    fun update(message: Message){
        dao.update(message)
    }

    fun delete(message: Message){
        dao.delete(message)
    }
}