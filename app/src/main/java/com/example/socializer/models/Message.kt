package com.example.socializer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey val id: Int,
    val text: String
)