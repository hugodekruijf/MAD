package com.example.level5task1.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.level5task1.database.NoteRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)

    val note = noteRepository.getNotepad()

}
