package com.example.charts.vitaldaten.bloodpressure.data

import com.example.charts.vitaldaten.data.Profile
import com.github.mikephil.charting.charts.LineChart

sealed class BloodPressureState {

    data class UpdateChart(
        val data: LineChart,
        val averageMap: AverageMeasures
    ) : BloodPressureState()

    data class UpdateProfile(val profile: Profile) : BloodPressureState()
}

data class AverageMeasures(
    val averageSysWeek: Int,
    val averageSysMonth: Int,
    val averageSysYear: Int,
    val averageDiaWeek: Int,
    val averageDiaMonth: Int,
    val averageDiaYear: Int,
    val averagePulseWeek: Int,
    val averagePulseMonth: Int,
    val averagePulseYear: Int
)
