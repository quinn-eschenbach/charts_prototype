package com.example.charts.hausdaten.water.data

class WaterMonthly (
    val consumption: Float,
    val monthOfYear: Int,
    val year: Int,
    val isWarm: Boolean,
    val isReference: Boolean
): WaterData