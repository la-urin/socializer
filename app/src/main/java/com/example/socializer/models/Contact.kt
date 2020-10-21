package com.example.socializer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey val lookupKey: String,
    @ColumnInfo(name = "lookup_key") val displayName: String
)