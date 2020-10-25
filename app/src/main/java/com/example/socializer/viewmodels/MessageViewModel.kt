package com.example.socializer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.socializer.models.Message
import com.example.socializer.models.MessageRepository
import com.example.socializer.models.database.AppDatabase
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MessageRepository
    val messages: LiveData<List<Message>>

    init {
        val dao = AppDatabase.getDatabase(application).messageDao()
        repository = MessageRepository(dao)
        messages = repository.messages
    }

    fun insert(message: Message) = viewModelScope.launch {
        repository.insert(message)
    }

    fun update(message: Message) = viewModelScope.launch {
        repository.update(message)
    }

    fun delete(message: Message) = viewModelScope.launch {
        repository.delete(message)
    }
}