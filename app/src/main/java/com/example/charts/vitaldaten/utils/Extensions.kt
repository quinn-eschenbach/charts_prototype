package com.example.charts.vitaldaten.utils

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

fun LineChart.formattedByZoomChange(lineData: LineData): ValueFormatter =
    object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            try { //Why do you need try catch for setting string values?
                val calendar = Calendar.getInstance()
                calendar.time = Date(
                    lineData.dataSets[0].getEntryForXValue(value, 1f).data.toString()
                        .toLong()
                )
                return when {
                    visibleXRange < 14f -> {
                        "${calendar.get(Calendar.DAY_OF_MONTH)}." +
                                "${calendar.get(Calendar.MONTH) + 1}." +
                                "${calendar.get(Calendar.YEAR)} " +
                                "${calendar.get(Calendar.HOUR_OF_DAY)}:" +
                                "${calendar.get(Calendar.MINUTE)}"
                    }
                    visibleXRange in 14f..40f -> {
                        "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}"
                    }
                    else -> "${calendar.get(Calendar.YEAR)}"
                }
            } catch (ex: Exception) {
                return ""
            }
        }
    }

fun LineChart.setupBasicLineChart() = this.apply {
    setDrawBorders(true)
    setDrawGridBackground(false)
    axisRight.setDrawGridLines(false)
    axisRight.setDrawAxisLine(false)
    axisRight.setDrawLabels(false)
    axisLeft.setDrawGridLines(false)
    axisLeft.setDrawLabels(true)
    axisLeft.setDrawAxisLine(false)
    xAxis.setDrawGridLines(false)
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.granularity = 1f
    description.isEnabled = false
}

fun LineDataSet.commonsSetup(
    colored: Int,
    _lineWidth: Float = 1f,
    withCircle: Boolean,
    isFilled: Boolean? = null
) {
    with(this) {
        color = colored
        setCircleColor(colored)
        setDrawCircles(withCircle)
        lineWidth = _lineWidth
        isFilled?.let { setDrawFilled(it) }
        valueFormatter = (object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })
    }
}