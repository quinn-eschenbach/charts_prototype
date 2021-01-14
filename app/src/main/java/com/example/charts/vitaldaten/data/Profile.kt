package com.example.charts.vitaldaten.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "profile_table")
class Profile (
    @PrimaryKey
    val id: Int,
    var name: String,
    var height: Float,
    var birthday: Long,
    var targetWeight: Float?,
    var minSys: Float,
    var maxSys: Float,
    var minDia: Float,
    var maxDia: Float,
    var minPulse: Float,
    var maxPulse: Float,
    var minBloodSugar: Float,
    var maxBloodSugar: Float,
    var warnRangeBloodSugar: Float
) :Serializable