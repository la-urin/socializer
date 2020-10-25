package com.example.socializer.models.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.socializer.models.Group
import com.example.socializer.models.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): LiveData<List<Message>>

    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)
}