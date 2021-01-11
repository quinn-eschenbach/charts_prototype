package com.example.charts.vitaldaten.bloodpressure.domain

import android.content.Context
import com.example.charts.charts.LineChartSetup
import com.example.charts.vitaldaten.bloodpressure.data.*
import com.example.charts.vitaldaten.data.DataSetup
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class BloodPressureInteractor @Inject constructor() {

    private lateinit var context: Context

    fun passContext(inputContext: Context){
        context = inputContext
    }

    fun getBloodPressureState(): Observable<BloodPressureState>{
        return stateObservable
    }

    private val stateObservable = PublishSubject.create<BloodPressureState>()

    fun getProfile(){
        stateObservable.onNext(
            UpdateProfile(DataSetup.profiles[0])
        )
    }

    fun clickHighlight(highlight: Highlight){
        stateObservable.onNext(
            when(highlight){
                Highlight.SYS -> HighlightSys
                Highlight.DIA -> HighlightDia
                Highlight.PULSE -> HighlightPulse
                Highlight.ALL -> HighlightAll
            }
        )
    }

    fun pushNewLineData(){
        stateObservable.onNext(UpdateChart(LineChartSetup.prepareBloodPressureData(DataSetup.getPressure(1), context)))
    }
}