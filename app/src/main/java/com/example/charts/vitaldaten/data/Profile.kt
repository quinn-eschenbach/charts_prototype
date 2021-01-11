package com.example.charts.vitaldaten.data

import java.io.Serializable
import java.util.*

class Profile (
    val id: Int,
    var name: String,
    var height: Float,
    var birthday: Date,
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