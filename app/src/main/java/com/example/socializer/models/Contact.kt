package com.example.socializer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "group_id") val groupId: Int,
    @ColumnInfo(name = "contact_id") val contactId: Long,
    @ColumnInfo(name = "lookup_key") val lookupKey: String,
    @ColumnInfo(name = "display_name") val displayName: String
)