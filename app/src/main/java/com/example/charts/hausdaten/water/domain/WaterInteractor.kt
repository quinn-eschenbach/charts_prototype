package com.example.charts.hausdaten.water.domain

import android.graphics.Color
import com.example.charts.charts.BarChartSetup
import com.example.charts.hausdaten.data.DataSetup
import com.example.charts.hausdaten.water.data.UpdateWaterChart
import com.example.charts.hausdaten.water.data.WaterPrepared
import com.example.charts.hausdaten.water.data.WaterState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class WaterInteractor @Inject constructor(){
    private val stateObservable = PublishSubject.create<WaterState>()

    private var isReferenceActive = false
    private var isLastYearActive = false

    private var setConsumptionYearly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterYearly(2021,false,false))
    private var setReferenceYearly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterYearly(2021,false,true))
    private var setLastYearYearly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterYearly(2021,false,false))

    private var setConsumptionMonthly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterMonthly(2021,1,false,false))
    private var setReferenceMonthly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterMonthly(2021,1,false,true))
    private var setLastYearMonthly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterMonthly(2021,1,false,false))

    private var setConsumptionWeekly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterWeekly(2021,1,false,false))
    private var setReferenceWeekly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterWeekly(2021,1,false,true))
    private var setLastYearWeekly: List<WaterPrepared>? =  BarChartSetup.prepareWaterData(DataSetup.getWaterWeekly(2021,1,false,false))


    private var setConsumption: List<WaterPrepared>? = setConsumptionMonthly
    private var setReference: List<WaterPrepared>? = null
    private var setLastYear: List<WaterPrepared>? = null

    private var colorConsumption = Color.BLUE
    private var colorReference = Color.GREEN
    private var colorLastYear = Color.RED

    private var isPrice = false

    fun getWaterState(): Observable<WaterState>{
        return stateObservable
    }

    fun getDataYearly(){
        setConsumption =  setConsumptionYearly
        setReference = setReferenceYearly
        setLastYear = setLastYearYearly
        pushNewBarData()
    }

    fun getDataMonthly(){
        setConsumption = setConsumptionMonthly
        setReference = setReferenceMonthly
        setLastYear = setLastYearMonthly
        pushNewBarData()
    }

    fun getDataWeekly(){
        setConsumption = setConsumptionWeekly
        setReference =  setReferenceWeekly
        setLastYear = setLastYearWeekly
        pushNewBarData()
    }

    fun toggleSet(set:Int, value: Boolean){
        when(set){
            1 -> isReferenceActive = value
            2 -> isLastYearActive = value
        }
        pushNewBarData()
    }

    private fun pushNewBarData(){
        val reference = if (isReferenceActive){
            setReference
        }else{
            null
        }
        val lastYear = if(isLastYearActive){
            setLastYear
        }
        else{
            null
        }
        stateObservable.onNext(UpdateWaterChart(BarChartSetup.setupWaterBarData(lastYear,colorLastYear,setConsumption,colorConsumption,reference, colorReference, isPrice)))
    }

    fun changeColor(set: Int, color: Int){
        when(set){
            0 -> colorConsumption = color
            1 -> colorReference = color
            2 -> colorLastYear = color
        }
        pushNewBarData()
    }

    fun toggleIsPrice(boolean: Boolean){
        isPrice = boolean
        pushNewBarData()
    }
}