package com.example.charts.vitaldaten.bloodpressure.presentation.ui

import com.example.charts.vitaldaten.bloodpressure.presentation.BloodPressureActions

interface BloodPressureEvents {
    fun sendActions(actions: BloodPressureActions)
}