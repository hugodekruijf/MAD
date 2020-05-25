package com.example.taskplanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskplanner.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskRoomDatabase  : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "TASK_DATABASE"

        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(context: Context): TaskRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(TaskRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TaskRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}