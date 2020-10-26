package com.example.socializer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.reflect.Constructor

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "group_id") val groupId: Int,
    @ColumnInfo var text: String
) {
    constructor(groupId: Int, text: String) : this(0, groupId, text)
}