package com.example.socializer.models.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.socializer.models.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :id LIMIT 1")
    fun getById(id: Int): Contact

    @Query("SELECT * FROM contact WHERE group_id = :groupId")
    fun getForGroupLive(groupId: Int): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE group_id = :groupId")
    fun getForGroup(groupId: Int): List<Contact>

    @Query("SELECT * FROM contact WHERE group_id = :groupId AND contact_id = :contactId LIMIT 1")
    fun getForGroupByExternalContactId(groupId: Int, contactId: Long) : Contact?;

    @Insert
    fun insert(contact: Contact)

    @Insert
    fun insertAll(vararg contacts: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM contact WHERE group_id = :groupId")
    fun deleteForGroup(groupId: Int)
}