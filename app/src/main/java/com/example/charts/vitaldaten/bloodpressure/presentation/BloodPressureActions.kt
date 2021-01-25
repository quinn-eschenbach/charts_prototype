package com.example.charts.vitaldaten.bloodpressure.presentation

import com.example.charts.vitaldaten.bloodpressure.data.Highlight
import com.example.charts.vitaldaten.bloodpressure.data.Highlight.ALL
import com.example.charts.vitaldaten.data.Profile
import com.github.mikephil.charting.charts.LineChart

sealed class BloodPressureActions {
    data class HighlightAll(
        val incomingChart: LineChart,
        val profile: Profile,
        val highlight: Highlight = ALL
    ) : BloodPressureActions()

    data class HighlightSys(
        val incomingChart: LineChart,
        val profile: Profile,
        val highlight: Highlight
    ) : BloodPressureActions()

    data class HighlightDia(
        val incomingChart: LineChart,
        val profile: Profile,
        val highlight: Highlight
    ) : BloodPressureActions()

    data class HighlightPulse(
        val incomingChart: LineChart,
        val profile: Profile,
        val highlight: Highlight
    ) : BloodPressureActions()

}