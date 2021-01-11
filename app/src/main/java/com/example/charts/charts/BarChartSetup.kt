package com.example.charts.charts

import android.graphics.Color
import com.example.charts.R
import com.example.charts.hausdaten.data.DataSetup
import com.example.charts.hausdaten.water.data.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode

object BarChartSetup {

    fun setupBarChart(chart: BarChart, data: BarData): BarChart {

        val barWidth =  1f / data.dataSetCount - 0.1f //0.25f
        val barSpace = 0.05f
        val groupSpace: Float = 1 - (barWidth + barSpace) * data.dataSetCount //mal anzahl gruppen
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 15f
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = data.dataSets[0].entryCount
        xAxis.mAxisMaximum = 12f
        xAxis.setAvoidFirstLastClipping(false)
        xAxis.spaceMin = 4f
        xAxis.spaceMax = 4f
        chart.data = data
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = data.dataSets[0].entryCount.toFloat() + 1
        if(data.dataSetCount > 1){
            xAxis.setCenterAxisLabels(true)
            chart.barData.barWidth = barWidth
            xAxis.axisMaximum = ( 0 + chart.barData.getGroupWidth(groupSpace,barSpace) * data.dataSets[0].entryCount.toFloat())
            chart.groupBars(0f, groupSpace, barSpace)
        }
        xAxis.valueFormatter = object : ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return data.dataSets[0].getEntryForXValue(value, 1f).data.toString()

            }
        }
        chart.axisLeft.axisMinimum = 0f
        chart.setFitBars(true)
        chart.setDrawGridBackground(false)
        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawLabels(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.animateY(500)
        chart.isHighlightFullBarEnabled = false
        chart.invalidate()
        return chart
    }

    fun setupWaterBarData(inputSet1:List<WaterPrepared>?, colorSet1: Int, inputSet2: List<WaterPrepared>?, colorSet2: Int, inputSet3: List<WaterPrepared>?,colorSet3: Int, isPrice: Boolean): BarData {
        val entriesSet1 = mutableListOf<BarEntry>()
        val entriesSet2 = mutableListOf<BarEntry>()
        val entriesSet3 = mutableListOf<BarEntry>()
        val sets = mutableListOf<BarDataSet>()
        val waterPrice = DataSetup.getWaterPrice()
        inputSet1?.let {
            it.forEachIndexed { index, elem ->
                entriesSet1.add(BarEntry(index.toFloat() + 1 , elem.consumption, elem.label))
            }
            val set1 = BarDataSet(entriesSet1,"")
            set1.valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return if(isPrice){
                        "${BigDecimal(value * waterPrice).setScale(2, RoundingMode.HALF_EVEN)}€"
                    }else{
                        "${BigDecimal(value.toDouble()).setScale(2, RoundingMode.HALF_EVEN)}m³"
                    }
                }
            }
            set1.color = colorSet1
            set1.setDrawIcons(true)
            set1.setDrawValues(true)
            set1.valueTextColor = Color.BLACK
            sets.add(set1)
        }

        inputSet2?.let {
            it.forEachIndexed { index, elem ->
                entriesSet2.add(BarEntry(index.toFloat() + 1, elem.consumption, elem.label))
            }
            val set2 = BarDataSet(entriesSet2,"")
            set2.color = colorSet2
            set2.setDrawIcons(true)
            set2.setDrawValues(true)
            set2.valueTextColor = Color.BLACK
            set2.valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return if(isPrice){
                        "${BigDecimal(value * waterPrice).setScale(2, RoundingMode.HALF_EVEN)}€"
                    }else{
                        "${BigDecimal(value.toDouble()).setScale(2, RoundingMode.HALF_EVEN)}m³"
                    }
                }
            }
            sets.add(set2)
        }
        inputSet3?.let {
            it.forEachIndexed { index, elem ->
                entriesSet3.add(BarEntry(index.toFloat() + 1, elem.consumption, elem.label))
            }
            val set3 = BarDataSet(entriesSet3,"")
            set3.color = colorSet3
            set3.setDrawIcons(true)
            set3.setDrawValues(true)
            set3.valueTextColor = Color.BLACK
            set3.valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return if(isPrice){
                        "${BigDecimal(value * waterPrice).setScale(2, RoundingMode.HALF_EVEN)}€"
                    }else{
                        "${BigDecimal(value.toDouble()).setScale(2, RoundingMode.HALF_EVEN)}m³"
                    }
                }
            }
            sets.add(set3)
        }

        return BarData(sets as List<IBarDataSet>?)
    }

    fun prepareWaterData(data: List<WaterData>): List<WaterPrepared>{
        val months = listOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember")
        val list = mutableListOf<WaterPrepared>()
        data.forEach {
            when(it){
                is WaterYearly ->{
                    list.add(WaterPrepared(it.consumption,"${it.year}"))
                }
                is WaterMonthly ->{
                    list.add(WaterPrepared(it.consumption, months[it.monthOfYear ]))
                }
                is WaterWeekly ->{
                    list.add(WaterPrepared(it.consumption, "KW${it.weekOfYear}"))
                }
            }
        }
        return  list
    }
}