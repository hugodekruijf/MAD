package com.example.reminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rockpaperschaar.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(reminder: Game)

    @Query("DELETE FROM gameTable")
    fun nukeTable()

    @Update
    suspend fun updateGame(reminder: Game)


}
