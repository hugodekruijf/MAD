package com.example.taskplanner.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.taskplanner.database.TaskRepository
import com.example.taskplanner.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application)  {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val taskRepository = TaskRepository(application.applicationContext)

    val tasks: LiveData<List<Task>> = taskRepository.getTasks()

    fun insertTask(task: Task) {
        ioScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun deleteTask(task: Task) {
        ioScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        ioScope.launch {
            taskRepository.deleteAllTasks()
        }
    }
}