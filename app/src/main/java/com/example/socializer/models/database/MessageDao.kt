package com.example.socializer.models.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.socializer.models.Contact
import com.example.socializer.models.Group
import com.example.socializer.models.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message WHERE group_id = :groupId")
    fun getForGroup(groupId: Int): List<Message>

    @Query("SELECT * FROM message WHERE group_id = :groupId")
    fun getForGroupLive(groupId: Int): LiveData<List<Message>>

    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)

    @Query("DELETE FROM message WHERE group_id = :groupId")
    fun deleteForGroup(groupId: Int)
}