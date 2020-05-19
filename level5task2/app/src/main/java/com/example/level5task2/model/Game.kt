package com.example.level5task2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Parcelize
@Entity(tableName = "gameTable")
data class Game(
    var title: String,
    var platfrom: String,
    var date: String,
    @PrimaryKey var id : Long? = null
) : Parcelable