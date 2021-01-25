package com.example.charts.charts

import android.content.Context
import android.graphics.Color
import com.example.charts.vitaldaten.utils.formattedByZoomChange
import com.example.charts.vitaldaten.utils.setupBasicLineChart
import com.example.charts.vitaldaten.bloodpressure.data.Highlight
import com.example.charts.vitaldaten.bloodsugar.BloodSugar
import com.example.charts.vitaldaten.bloodsugar.TextDrawable
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.weight.data.Weight
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*
import java.util.Calendar.*


object LineChartSetup {

    fun getSugarLineChart(chart: LineChart) = chart.apply {
        setupBasicLineChart()
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = 24f
        axisLeft.setDrawLabels(false)
        xAxis.labelCount = 24
        axisLeft.axisMinimum = 60f
        axisLeft.axisMaximum = 250f
        fitScreen()
        xAxis.setDrawGridLines(true)
        xAxis.valueFormatter = (object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}:00"
            }
        })
    }

    fun prepareWeightSet(data: List<Weight>): LineData {
        val entries = mutableListOf<Entry>()
        // 1. Nach TimeStamp sortieren (größte, also letzte messung zu letzt)
        val dataSorted = data.sortedBy { it.timeStamp }

        dataSorted.forEachIndexed { i, weight ->
            val date = Date(weight.timeStamp)
            val calendar = Calendar.getInstance()
            calendar.time = date
            entries.add(Entry(i.toFloat(), weight.weight, weight.timeStamp))
        }
        entries.sortBy { it.x }

        val set = LineDataSet(entries, "").apply {
            setDrawIcons(true)
            color = Color.BLACK
            lineWidth = 1f
            valueTextSize = 9f
            setDrawCircles(false)
            formSize = 15f
            valueFormatter = (object : ValueFormatter() {
                override fun getPointLabel(entry: Entry?): String {
                    return "${entry?.y}"
                }
            })
        }

        return LineData(set)
    }

    fun prepareSugarSet(data: List<BloodSugar>, profile: Profile, context: Context): LineData {
        val entries = mutableListOf<Entry>()
        data.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = Date(it.timeStamp)
            val rectColor = when {
                it.bloodSugar < profile.minBloodSugar || it.bloodSugar > profile.maxBloodSugar -> Color.RED
                it.bloodSugar < profile.minBloodSugar + profile.warnRangeBloodSugar || it.bloodSugar > profile.maxBloodSugar - profile.warnRangeBloodSugar -> Color.YELLOW
                else -> Color.GREEN
            }
            val drawable =
                TextDrawable(
                    context.resources,
                    it.bloodSugar.toInt().toString(),
                    rectColor
                )
            entries.add(
                Entry(calendar.get(HOUR_OF_DAY).toFloat(), it.bloodSugar, drawable)
            )
        }
        entries.sortBy {
            it.x
        }
        val set = LineDataSet(entries, "")
        set.setDrawCircles(false)
        set.setDrawIcons(true)
        set.color = Color.BLACK
        set.isHighlightEnabled = false
        return LineData(set)
    }

    fun getLineChart(
        incomingChart: LineChart,
        lineData: LineData,
        profile: Profile,
        highlight: Highlight?
    ): LineChart {
        with(incomingChart.setupBasicLineChart()) {
            data = lineData
            axisLeft.axisMinimum = lineData.yMin - 20
            axisLeft.axisMaximum = lineData.yMax + 20
            fitScreen()
            setVisibleXRangeMaximum(31f)
            moveViewToX(lineData.xMax)
            setVisibleXRangeMaximum(365f)
            setVisibleXRangeMinimum(3f)
            xAxis.spaceMax = 1f
            xAxis.labelCount = 31
            xAxis.valueFormatter = formattedByZoomChange(lineData)
            axisLeft.removeAllLimitLines()

            if (highlight == null) {
                profile.targetWeight?.let { targetWeight ->
                    val lastValue =
                        lineData.dataSets[0].getEntryForXValue(lineData.dataSets[0].xMax, 1f).y
                    if (lastValue < targetWeight) {
                        axisLeft.addLimitLine(getLimitLine(targetWeight, Color.GREEN))
                    } else {
                        axisLeft.addLimitLine(getLimitLine(targetWeight, Color.RED))
                    }
                    axisLeft.enableGridDashedLine(10f, 10f, 0f)
                    axisLeft.setDrawLimitLinesBehindData(false)
                }
            }
            animateX(1000)

            return this
        }
    }


    private fun getLimitLine(value: Float, color: Int) =
        LimitLine(value, "").apply {
            lineWidth = 1f
            labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            textSize = 10f
            lineColor = color
        }
}