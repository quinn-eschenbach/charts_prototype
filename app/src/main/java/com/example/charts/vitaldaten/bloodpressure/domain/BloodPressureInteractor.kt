package com.example.charts.vitaldaten.bloodpressure.domain

import com.example.charts.vitaldaten.bloodpressure.data.*
import com.example.charts.vitaldaten.data.DataSetup
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class BloodPressureInteractor @Inject constructor() {

    fun getBloodPressureState(): Observable<BloodPressureState>{
        return stateObservable
    }

    private val stateObservable = PublishSubject.create<BloodPressureState>()

    fun getData(){
        stateObservable.onNext(
            UpdateChart(DataSetup.getPressure(1))
        )
    }

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
}