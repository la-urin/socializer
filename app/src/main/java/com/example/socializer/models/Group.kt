package com.example.socializer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String
) {
    constructor(name: String) : this(0, name)
}