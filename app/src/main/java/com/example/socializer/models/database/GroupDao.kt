package com.example.socializer.models.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.socializer.models.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM `group` ORDER BY name ASC")
    fun getAll(): List<Group>

    @Query("SELECT * FROM `group` WHERE id = :id LIMIT 1")
    fun getById(id: Int): Group

    @Insert
    fun insert(group: Group)

    @Update
    fun update(group: Group)

    @Delete
    fun delete(group: Group)

    @Query("SELECT * FROM `group` ORDER BY name ASC")
    fun getGroupsSortedByAlphaAsc(): LiveData<List<Group>>

    @Query("SELECT * FROM `group` ORDER BY name DESC")
    fun getGroupsSortedByAlphaDesc(): LiveData<List<Group>>
}