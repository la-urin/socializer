package com.example.socializer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.socializer.models.Contact
import com.example.socializer.models.Group
import com.example.socializer.models.Message

@Database(entities = [Group::class, Contact::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao
}