package com.example.socializer.models

import androidx.lifecycle.LiveData
import com.example.socializer.models.database.MessageDao

class MessageRepository(private val dao: MessageDao) {
    val messages: LiveData<List<Message>> = dao.getAll()

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