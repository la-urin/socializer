package com.example.socializer.models.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.socializer.models.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM `group`")
    fun getAll(): LiveData<List<Group>>

    @Query("SELECT * FROM `group` WHERE id = :id LIMIT 1")
    fun getById(id: Int): Group

    @Insert
    fun insert(group: Group)

    @Insert
    fun insertAll(vararg groups: Group)

    @Delete
    fun delete(group: Group)
}