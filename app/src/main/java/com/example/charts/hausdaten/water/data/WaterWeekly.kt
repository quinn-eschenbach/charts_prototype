package com.example.charts.hausdaten.water.data

class WaterWeekly(
    val consumption: Float,
    val weekOfYear: Int,
    val year: Int,
    val isWarm: Boolean,
    val isReference: Boolean
): WaterData