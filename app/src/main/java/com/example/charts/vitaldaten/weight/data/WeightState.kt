package com.example.charts.vitaldaten.weight.data

import com.example.charts.vitaldaten.data.Profile

sealed class WeightState
class UpdateChart(val data: List<Weight>): WeightState()
class UpdateProfile(val profile: Profile): WeightState()