package com.example.charts.vitaldaten.weight.domain

import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState
import com.example.charts.vitaldaten.data.DataSetup
import com.example.charts.vitaldaten.weight.data.UpdateChart
import com.example.charts.vitaldaten.weight.data.UpdateProfile
import com.example.charts.vitaldaten.weight.data.WeightState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class WeightInteractor @Inject constructor() {

    private val stateObservable = PublishSubject.create<WeightState>()

    fun getWeightState(): Observable<WeightState> {
        return stateObservable
    }

    fun getProfile(){
        stateObservable.onNext(UpdateProfile(DataSetup.profiles[0]))
    }

    fun getData(){
        stateObservable.onNext(UpdateChart(DataSetup.getWeight(1, null)))
    }
}