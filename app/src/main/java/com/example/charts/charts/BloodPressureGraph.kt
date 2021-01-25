package com.example.charts.charts

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.charts.R
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressure
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

object BloodPressureGraph {

    fun prepareBloodPressureData(data: List<BloodPressure>, context: Context): LineData {
        val entriesSys = mutableListOf<Entry>()
        val entriesDia = mutableListOf<Entry>()
        val entriesPulse = mutableListOf<Entry>()
        // 1. Nach TimeStamp sortieren (größte, also letzte messung zu letzt)
        val dataSorted = data.sortedBy { it.timeStamp }
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

    private fun LineDataSet.commonsSetup(
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
            valueFormatter = (object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            })
        }
    }
}