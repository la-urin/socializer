package com.example.socializer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.socializer.models.Contact
import com.example.socializer.models.Group
import com.example.socializer.models.Message

@Database(entities = [Group::class, Contact::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "app.db"

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DB_NAME
                        ).build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}