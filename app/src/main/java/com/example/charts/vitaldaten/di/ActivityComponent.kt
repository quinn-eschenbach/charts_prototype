package com.example.charts.vitaldaten.di

import com.example.charts.vitaldaten.bloodpressure.presentation.ui.BloodPressureActivity
import com.example.charts.vitaldaten.bloodsugar.BloodSugarActivity
import com.example.charts.vitaldaten.data.ProfileDao
import com.example.charts.vitaldaten.prensentation.ui.VitalActivity
import com.example.charts.vitaldaten.settings.SettingsActivity
import com.example.charts.vitaldaten.weight.presentation.ui.WeightActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(bloodPressureActivity: BloodPressureActivity)

    fun inject(bloodSugarActivity: BloodSugarActivity)

    fun inject(weightActivity: WeightActivity)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(vitalActivity: VitalActivity)

}