package com.example.charts.vitaldaten.bloodpressure.presentation

import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState
import com.example.charts.vitaldaten.bloodpressure.domain.BloodPressureInteractor
import com.example.charts.vitaldaten.bloodpressure.presentation.ui.BloodPressureEvents
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class BloodPressureViewModel @Inject constructor(
    private val interactor: BloodPressureInteractor
) : BloodPressureEvents by interactor {
    fun fetchBloodPressureStates(): Observable<BloodPressureState> =
        interactor.getBloodPressureState()

    fun getProfile() = interactor.getProfile()
}