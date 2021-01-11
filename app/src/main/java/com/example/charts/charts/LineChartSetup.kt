package com.example.charts.charts

import android.content.Context
import android.graphics.Color
import com.example.charts.R
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressure
import com.example.charts.vitaldaten.bloodsugar.TextDrawable
import com.example.charts.vitaldaten.bloodsugar.BloodSugar
import com.example.charts.vitaldaten.data.*
import com.example.charts.vitaldaten.weight.data.Weight
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.lang.Exception
import java.util.*


object LineChartSetup {

    fun getSugarLineChart(chart: LineChart): LineChart {
        val chart = LineChartSetup.setupBasicLineChart(chart)
        chart.xAxis.axisMinimum = 0f
        chart.xAxis.axisMaximum = 24f
        chart.axisLeft.setDrawLabels(false)
        chart.xAxis.labelCount = 24
        chart.axisLeft.axisMinimum = 60f
        chart.axisLeft.axisMaximum = 250f
        chart.fitScreen()
        chart.xAxis.setDrawGridLines(true)
        chart.xAxis.valueFormatter = (object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}:00"
            }
        })
        return chart
    }

    fun prepareWeightSet(data: List<Weight>): LineData {
        val entries = mutableListOf<Entry>()
        // 1. Nach TimeStamp sortieren (größte, also letzte messung zu letzt)
        val dataSorted = data.sortedBy {
            it.timeStamp
        }
        dataSorted.forEachIndexed { i, weight ->
            val date = Date(weight.timeStamp)
            val calendar = Calendar.getInstance()
            calendar.time = date
            entries.add(Entry(i.toFloat(), weight.weight))
        }
        entries.sortBy {
            it.x
        }
        val set = LineDataSet(entries, "")
        set.setDrawIcons(true)
        set.color = Color.BLACK
        set.lineWidth = 1f
        set.valueTextSize = 9f
        set.setDrawCircles(false)
        set.formSize = 15f
        set.valueFormatter = (object : ValueFormatter() {
            override fun getPointLabel(entry: Entry?): String {
                return "${entry?.y}"
            }
        })
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
                Entry(calendar.get(Calendar.HOUR_OF_DAY).toFloat(), it.bloodSugar, drawable)
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
        data: List<ILineValues>,
        lineData: LineData,
        profile: Profile,
        targetWeight: Float?
    ): LineChart {
        val chart = setupBasicLineChart(incomingChart)
        val dataSorted = data.sortedBy {
            it.timeStamp
        }
        chart.data = lineData
        chart.axisLeft.axisMinimum = lineData.yMin - 20
        chart.axisLeft.axisMaximum = lineData.yMax + 20
        chart.fitScreen()
        chart.setVisibleXRangeMaximum(31f)
        chart.moveViewToX(lineData.xMax)
        chart.setVisibleXRangeMaximum(365f)
        chart.setVisibleXRangeMinimum(3f)
        chart.xAxis.spaceMax = 1f
        chart.xAxis.labelCount = 31
        chart.xAxis.valueFormatter = (object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                try {
                    val calendar = Calendar.getInstance()
                    calendar.time = Date(dataSorted[value.toInt()].timeStamp)
                    val zoomLevel = chart.visibleXRange
                    return when {
                        zoomLevel < 14f -> {
                            "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${calendar.get(
                                Calendar.YEAR
                            )} ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
                        }
                        zoomLevel > 14f && zoomLevel < 40f -> {
                            "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}"
                        }
                        else -> {
                            "${calendar.get(Calendar.YEAR)}"
                        }
                    }
                } catch (ex: Exception) {
                    return ""
                }
            }
        })

        chart.axisLeft.removeAllLimitLines()

        /*when (highlight) {
            HIGHLIGHT.ALL -> {
                chart.axisLeft.removeAllLimitLines()
            }
            HIGHLIGHT.DIA -> {
                chart.axisLeft.removeAllLimitLines()
                chart.axisLeft.addLimitLine(getLimitLine(profile.minDia, Color.BLUE))
                chart.axisLeft.addLimitLine(getLimitLine(profile.maxDia, Color.BLUE))
            }
            HIGHLIGHT.PULSE -> {
                chart.axisLeft.removeAllLimitLines()
                chart.axisLeft.addLimitLine(getLimitLine(profile.minPulse, Color.GREEN))
                chart.axisLeft.addLimitLine(getLimitLine(profile.maxPulse, Color.GREEN))
            }
            HIGHLIGHT.SYS -> {
                chart.axisLeft.removeAllLimitLines()
                chart.axisLeft.addLimitLine(getLimitLine(profile.minSys, Color.RED))
                chart.axisLeft.addLimitLine(getLimitLine(profile.maxSys, Color.RED))
            }
        }*/
        if (targetWeight != null) {
            chart.axisLeft.addLimitLine(getLimitLine(targetWeight, Color.RED))
            chart.axisLeft.enableGridDashedLine(10f, 10f, 0f);
            chart.axisLeft.setDrawLimitLinesBehindData(false);
        }

        chart.animateX(1000)

        return chart
    }

    fun prepareBloodPressureData(data: List<BloodPressure>,  context: Context): LineData {
        val entriesSys = mutableListOf<Entry>()
        val entriesDia = mutableListOf<Entry>()
        val entriesPulse = mutableListOf<Entry>()
        // 1. Nach TimeStamp sortieren (größte, also letzte messung zu letzt)
        val dataSorted = data.sortedBy {
            it.timeStamp
        }
        // 2. Entries Listen erstellen, x-Wert ist der Index
        dataSorted.forEachIndexed { index, it ->
            entriesSys.add(Entry(index.toFloat(), it.sys))
            entriesDia.add(Entry(index.toFloat(), it.dia))
            entriesPulse.add(Entry(index.toFloat(), it.pulse))
        }
        // 3. erneut nach x sortieren (wenn nicht kann die library abschmieren)
        entriesSys.sortBy {
            it.x
        }
        entriesDia.sortBy {
            it.x
        }
        entriesPulse.sortBy {
            it.x
        }
        val setSys = LineDataSet(entriesSys, "Systolisch")
        val setDia = LineDataSet(entriesDia, "Diastolisch")
        val setPuls = LineDataSet(entriesPulse, "Puls")

        /*when (highlight) {
            HIGHLIGHT.ALL -> {
                setSys.setDrawValues(true)
                setPuls.setDrawValues(true)
                setDia.setDrawValues(true)
            }
            HIGHLIGHT.DIA -> {
                setSys.setDrawValues(false)
                setPuls.setDrawValues(false)
                setDia.setDrawValues(true)
            }
            HIGHLIGHT.PULSE -> {
                setSys.setDrawValues(false)
                setPuls.setDrawValues(true)
                setDia.setDrawValues(false)
            }
            HIGHLIGHT.SYS -> {
                setSys.setDrawValues(true)
                setPuls.setDrawValues(false)
                setDia.setDrawValues(false)
            }
        }*/
        setSys.color = Color.RED
        setSys.setCircleColor(Color.RED)
        setSys.setDrawCircles(false)
        setSys.lineWidth = 3f
        setSys.setDrawFilled(true)
        setSys.fillDrawable = context.getDrawable(R.drawable.fade_red)
        setSys.valueFormatter = (object : com.github.mikephil.charting.formatter.ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })
        setDia.color = Color.BLUE
        setDia.setCircleColor(Color.BLUE)
        setDia.setDrawCircles(false)
        setDia.lineWidth = 3f
        setDia.setDrawFilled(true)
        setDia.fillDrawable = context.getDrawable(R.drawable.fade_blue)
        setDia.valueFormatter = (object : com.github.mikephil.charting.formatter.ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })

        setPuls.color = Color.GREEN
        setPuls.setCircleColor(Color.GREEN)
        setPuls.setDrawCircles(false)
        setPuls.valueFormatter = (object : com.github.mikephil.charting.formatter.ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })
        return LineData(setSys, setDia, setPuls)
    }

    fun setupBasicLineChart(chart: LineChart): LineChart {
        chart.setDrawBorders(true)
        chart.setDrawGridBackground(false)
        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawLabels(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.setDrawLabels(true)
        chart.axisLeft.setDrawAxisLine(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.granularity = 1f
        chart.description.isEnabled = false
        return chart
    }

    private fun getLimitLine(value: Float, color: Int): LimitLine {
        val limitLine = LimitLine(value, "")
        limitLine.lineWidth = 1f
        limitLine.enableDashedLine(10f, 10f, 0f)
        limitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        limitLine.textSize = 10f
        limitLine.lineColor = color
        return limitLine
    }
}