package com.example.charts.vitaldaten.bloodpressure.data

import com.example.charts.vitaldaten.data.Profile

sealed class BloodPressureState
object HighlightAll : BloodPressureState()
object HighlightSys : BloodPressureState()
object HighlightDia : BloodPressureState()
object HighlightPulse : BloodPressureState()
data class UpdateChart(val data: List<BloodPressure>) : BloodPressureState()
data class UpdateProfile(val profile: Profile) : BloodPressureState()