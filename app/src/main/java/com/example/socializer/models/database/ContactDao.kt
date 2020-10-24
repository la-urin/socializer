package com.example.socializer.models.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.socializer.models.Contact
import com.example.socializer.models.Group

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE id = :id LIMIT 1")
    fun getById(id: Int): Contact

    @Insert
    fun insert(contact: Contact)

    @Insert
    fun insertAll(vararg contacts: Contact)

    @Delete
    fun delete(contact: Contact)
}