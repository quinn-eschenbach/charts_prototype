package com.example.charts.vitaldaten.bloodpressure.data

import com.example.charts.vitaldaten.data.Profile
import com.github.mikephil.charting.data.LineData

sealed class BloodPressureState
object HighlightAll : BloodPressureState()
object HighlightSys : BloodPressureState()
object HighlightDia : BloodPressureState()
object HighlightPulse : BloodPressureState()
data class UpdateChart(val data: LineData) : BloodPressureState()
data class UpdateProfile(val profile: Profile) : BloodPressureState()