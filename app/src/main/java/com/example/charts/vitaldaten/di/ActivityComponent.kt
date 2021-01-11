package com.example.charts.vitaldaten.di

import com.example.charts.vitaldaten.bloodpressure.presentation.ui.BloodPressureActivity
import com.example.charts.vitaldaten.bloodsugar.BloodSugarActivity
import com.example.charts.vitaldaten.settings.SettingsActivity
import com.example.charts.vitaldaten.weight.presentation.ui.WeightActivity
import dagger.Component

@Component
interface ActivityComponent {

    fun inject(bloodPressureActivity: BloodPressureActivity)

    fun inject(bloodSugarActivity: BloodSugarActivity)

    fun inject(weightActivity: WeightActivity)

    fun inject(settingsActivity: SettingsActivity)
}