package com.example.taskplanner.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.taskplanner.database.TaskRepository
import com.example.taskplanner.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditActivityViewModel (application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val task = MutableLiveData<Task?>()
    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()

    fun updateTask() {
        if (isTaskValid()) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    taskRepository.updateTask(task.value!!)
                }
                success.value = true
            }
        }
    }


    private fun isTaskValid(): Boolean {
        return when {
            task.value == null -> {
                error.value = "Task must not be null"
                false
            }
            task.value!!.name.isBlank() -> {
                error.value = "Project name must not be empty"
                false
            }
            else -> true
        }
    }

}