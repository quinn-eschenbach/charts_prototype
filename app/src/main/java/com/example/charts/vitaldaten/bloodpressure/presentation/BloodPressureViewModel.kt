package com.example.charts.vitaldaten.bloodpressure.presentation

import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState
import com.example.charts.vitaldaten.bloodpressure.data.Highlight
import com.example.charts.vitaldaten.bloodpressure.domain.BloodPressureInteractor
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject

class BloodPressureViewModel @Inject constructor(
    private val interactor: BloodPressureInteractor
) {
    fun fetchBloodPressureStates(): Observable<BloodPressureState> =
        interactor.getBloodPressureState()

    fun click( highlight: Highlight) = interactor.clickHighlight(highlight)

    fun getData() = interactor.getData()

    fun getProfile() = interactor.getProfile()
}