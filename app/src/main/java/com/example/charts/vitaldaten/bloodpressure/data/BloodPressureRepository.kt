package com.example.charts.vitaldaten.bloodpressure.data

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.charts.R
import com.example.charts.vitaldaten.utils.commonsSetup
import com.example.charts.vitaldaten.utils.formattedByZoomChange
import com.example.charts.vitaldaten.utils.setupBasicLineChart
import com.example.charts.vitaldaten.data.Profile
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BloodPressureRepository @Inject constructor() {

    fun getLineChart(
        incomingChart: LineChart,
        profile: Profile,
        highlight: Highlight?
    ): LineChart {
        with(incomingChart.setupBasicLineChart()) {
            val lineData = prepareBloodPressureData(context)
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

            setHighlightedGraph(highlight, profile)

            animateX(1000)

            return this
        }
    }

    private fun prepareBloodPressureData(context: Context): LineData {
        val entriesSys = mutableListOf<Entry>()
        val entriesDia = mutableListOf<Entry>()
        val entriesPulse = mutableListOf<Entry>()
        // 1. Nach TimeStamp sortieren (größte, also letzte messung zu letzt)
        val dataSorted = getPressure().sortedBy { it.timeStamp }
        // 2. Entries Listen erstellen, x-Wert ist der Index
        dataSorted.forEachIndexed { index, it ->
            entriesSys.add(Entry(index.toFloat(), it.sys, it.timeStamp))
            entriesDia.add(Entry(index.toFloat(), it.dia, it.timeStamp))
            entriesPulse.add(Entry(index.toFloat(), it.pulse, it.timeStamp))
        }
        entriesSys.sortBy { it.x }
        entriesDia.sortBy { it.x }
        entriesPulse.sortBy { it.x }
        val setSys = LineDataSet(entriesSys, "Systolisch")
        val setDia = LineDataSet(entriesDia, "Diastolisch")
        val setPuls = LineDataSet(entriesPulse, "Puls")

        return assembleLineChart(setSys, setDia, setPuls, context)
    }


    private fun LineChart.setHighlightedGraph(
        highlight: Highlight?,
        profile: Profile
    ) {
        axisLeft.removeAllLimitLines()
        when (highlight) {
            Highlight.ALL -> {
                axisLeft.removeAllLimitLines()
            }
            Highlight.DIA -> {
                axisLeft.addLimitLine(getLimitLine(profile.minDia, Color.BLUE))
                axisLeft.addLimitLine(getLimitLine(profile.maxDia, Color.BLUE))
            }
            Highlight.PULSE -> {
                axisLeft.addLimitLine(getLimitLine(profile.minPulse, Color.GREEN))
                axisLeft.addLimitLine(getLimitLine(profile.maxPulse, Color.GREEN))
            }
            Highlight.SYS -> {
                axisLeft.addLimitLine(getLimitLine(profile.minSys, Color.RED))
                axisLeft.addLimitLine(getLimitLine(profile.maxSys, Color.RED))
            }
        }
    }

    private fun getLimitLine(value: Float, color: Int) =
        LimitLine(value, "").apply {
            lineWidth = 1f
            labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            textSize = 10f
            lineColor = color
        }

    private fun assembleLineChart(
        setSys: LineDataSet,
        setDia: LineDataSet,
        setPuls: LineDataSet,
        context: Context
    ): LineData {

        setSys.apply {
            commonsSetup(colored = Color.RED, _lineWidth = 3f, withCircle = false, isFilled = true)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.fade_red)
        }

        setDia.apply {
            commonsSetup(colored = Color.BLUE, _lineWidth = 3f, withCircle = false, isFilled = true)
            color = Color.BLUE
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.fade_blue)
        }

        setPuls.apply {
            commonsSetup(colored = Color.GREEN, withCircle = false)
        }

        return LineData(setSys, setDia, setPuls)
    }


    private fun getPressure(id: Int = 1): List<BloodPressure> {
        val list = mutableListOf<BloodPressure>()
        for (i in 1..500) {
            val date =
                (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(i.toLong()) * (1..50).random())
            list.add(
                BloodPressure(
                    sys = (110..130).random().toFloat(),
                    dia = (80..90).random().toFloat(),
                    pulse = (60..80).random().toFloat(),
                    timeStamp = date,
                    profileId = id
                )
            )
        }
        return list
    }
}