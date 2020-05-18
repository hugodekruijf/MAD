package com.example.level5task2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.level5task2.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM gameTable")
    fun getAllGames(): LiveData<Game?>

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM gameTable")
    fun nukeTable()

}