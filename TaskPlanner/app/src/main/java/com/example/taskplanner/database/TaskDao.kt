package com.example.taskplanner.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskplanner.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM taskTable")
    fun getAllTasks(): LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM taskTable")
    fun nukeTable()
}