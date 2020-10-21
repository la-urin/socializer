package com.example.socializer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.socializer.models.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE lookup_key LIKE :lookupKey LIMIT 1")
    fun findByLookupKey(lookupKey: String): Contact

    @Insert
    fun insert(contact: Contact)

    @Insert
    fun insertAll(vararg contacts: Contact)

    @Delete
    fun delete(contact: Contact)
}