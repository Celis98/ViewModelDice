package com.example.viewmodeldice.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.viewmodeldice.data.db.ResultsDao
import com.example.viewmodeldice.data.entities.ResultEntity

@Database(entities = [ResultEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultsDao
}