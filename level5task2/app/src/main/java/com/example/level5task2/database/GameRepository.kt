package com.example.level5task2.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.level5task2.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()  ?: MutableLiveData(emptyList())
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.nukeTable()
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

}