package com.example.rockpaperschaar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "gameTable")
data class Game (
    @ColumnInfo(name = "game")
    var gameOutcome: String,
    var gameTime: String,
    var gameHuman: String,
    var gameComputer: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
    ) : Parcelable