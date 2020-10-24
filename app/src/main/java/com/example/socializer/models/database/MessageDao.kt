package com.example.socializer.models.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.socializer.models.Group
import com.example.socializer.models.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE id = :id LIMIT 1")
    fun getById(id: Int): Message

    @Insert
    fun insert(message: Message)

    @Insert
    fun insertAll(vararg messages: Message)

    @Delete
    fun delete(message: Message)
}