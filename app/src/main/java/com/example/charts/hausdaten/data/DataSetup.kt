package com.example.charts.hausdaten.data

import com.example.charts.hausdaten.water.data.WaterMonthly
import com.example.charts.hausdaten.water.data.WaterWeekly
import com.example.charts.hausdaten.water.data.WaterYearly

object DataSetup {
    fun getWaterWeekly(year: Int, weekStart: Int, isWarm: Boolean,  isReference: Boolean): List<WaterWeekly>{
        val list = mutableListOf<WaterWeekly>()
        for(i in 1..52){
            var consumption = 0.840f + ((0..20).random() * 0.01f)
            if(isReference){
                consumption = 0.875f
            }
            list.add(
                WaterWeekly(consumption,weekStart + i - 1, year, isWarm, isReference)
            )
        }
        return list
    }
    fun getWaterMonthly(year: Int, monthStart: Int, isWarm: Boolean, isReference: Boolean): List<WaterMonthly>{
        val list = mutableListOf<WaterMonthly>()
        for(i in 1..12){
            var consumption = 3.570f + ((0..20).random() * 0.1f)
            if(isReference){
                consumption = 3.875f
            }
            list.add(
                WaterMonthly(consumption, i - 1, year, isWarm, isReference)
            )
        }
        return list
    }
    fun getWaterYearly(year: Int,  isWarm: Boolean, isReference: Boolean): List<WaterYearly>{
        val list = mutableListOf<WaterYearly>()
        for(i in 1..3){
            var consumption = 45.5f + ((0..100).random() * 0.1f)
            if(isReference){
                consumption = 46.5f
            }
            list.add(
                WaterYearly(consumption, year - i + 1, isWarm, isReference)
            )
        }
        return list
    }


    fun getWaterPrice(): Double {
        return 1.694
    }
}