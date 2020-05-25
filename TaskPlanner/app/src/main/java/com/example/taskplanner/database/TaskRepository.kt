package com.example.taskplanner.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskplanner.model.Task

class TaskRepository(context: Context) {
    private val taskDao: TaskDao

    init {
        val database = TaskRoomDatabase.getDatabase(context)
        taskDao = database!!.taskDao()
    }

    fun getTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks() ?: MutableLiveData(emptyList())
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.nukeTable()
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}