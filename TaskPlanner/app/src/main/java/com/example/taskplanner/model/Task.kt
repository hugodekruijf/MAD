package com.example.taskplanner.model

import android.app.ActivityManager
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "taskTable")
data class Task(
    var name: String,
    var timeEstimate: Int,
    var deadline: String,
    var taskDescription: String,
    @PrimaryKey var id : Long? = null
) : Parcelable