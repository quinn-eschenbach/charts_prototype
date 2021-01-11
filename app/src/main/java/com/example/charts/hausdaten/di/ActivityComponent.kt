package com.example.charts.hausdaten.di

import com.example.charts.hausdaten.water.presentation.ui.WaterActivity
import dagger.Component

@Component
interface ActivityComponent {
    fun inject(waterActivity: WaterActivity)
}