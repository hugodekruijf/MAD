package com.example.level5task2.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.level5task2.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getNotepad(): LiveData<Game?> {
        return gameDao.getAllGames()
    }

    suspend fun deleteAllGames() {
        gameDao.nukeTable()
    }

    suspend fun deleteReminder(game: Game) {
        gameDao.deleteGame(game)
    }

}