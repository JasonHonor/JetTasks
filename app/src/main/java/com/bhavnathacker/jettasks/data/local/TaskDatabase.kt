package com.bhavnathacker.jettasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bhavnathacker.jettasks.domain.model.Task

@Database(entities = [Task::class], version = 4, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}