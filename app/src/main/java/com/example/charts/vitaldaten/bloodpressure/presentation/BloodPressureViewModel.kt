package com.example.charts.vitaldaten.bloodpressure.presentation

import android.content.Context
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

    fun clickHighlight(highlight: Highlight) = interactor.clickHighlight(highlight)

    fun setContext(context: Context) = interactor.passContext(context)

    fun getData() = interactor.pushNewLineData()

    fun getProfile() = interactor.getProfile()
}