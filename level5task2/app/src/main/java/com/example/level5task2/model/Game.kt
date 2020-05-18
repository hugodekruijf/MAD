package com.example.level5task2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "gameTable")
data class Game(
    var title: String,
    var platfrom: String,
    var day: String,
    var month: String,
    var year: String,
    @PrimaryKey var id : Long? = null
) : Parcelable