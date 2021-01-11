package com.example.charts.hausdaten.water.data

import com.github.mikephil.charting.data.BarData

sealed class WaterState
class UpdateWaterChart(val data: BarData): WaterState()