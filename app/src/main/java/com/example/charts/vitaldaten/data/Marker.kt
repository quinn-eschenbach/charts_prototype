package com.example.charts.vitaldaten.data

import java.util.*

class Marker (
    val type: MarkerType,
    val message: String,
    val date: Date,
    val profileId: Int
)

enum class MarkerType{
    BLOODPRESSURE, WEIGHT, BLOODSUGAR
}