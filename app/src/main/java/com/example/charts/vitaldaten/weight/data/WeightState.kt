package com.example.charts.vitaldaten.weight.data

import com.example.charts.vitaldaten.data.Profile
import com.github.mikephil.charting.data.LineData

sealed class WeightState
class UpdateChart(val data: LineData): WeightState()
class UpdateProfile(val profile: Profile): WeightState()