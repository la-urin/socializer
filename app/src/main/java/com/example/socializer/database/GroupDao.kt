package com.example.socializer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.socializer.models.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM [group]")
    fun getAll(): List<Group>

    @Query("SELECT * FROM [group] WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Group

    @Insert//(onConflict = OnConflictStrategy.ABORT)
    fun insert(group: Group)

    @Insert
    fun insertAll(vararg groups: Group)

    @Delete
    fun delete(group: Group)
}