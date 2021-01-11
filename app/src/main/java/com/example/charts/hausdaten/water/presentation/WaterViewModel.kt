package com.example.charts.hausdaten.water.presentation

import com.example.charts.hausdaten.water.data.WaterState
import com.example.charts.hausdaten.water.domain.WaterInteractor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class WaterViewModel @Inject constructor(
    private val interactor: WaterInteractor
) {
    fun fetchWaterStates() = interactor.getWaterState()

    fun getYearly() = interactor.getDataYearly()

    fun getMonthly() = interactor.getDataMonthly()

    fun getWeekly() = interactor.getDataWeekly()

    fun toggleSet(set: Int, value: Boolean) = interactor.toggleSet(set, value)

    fun changeColor(set: Int, color: Int) = interactor.changeColor(set,color)

    fun toggleIsPrice(boolean: Boolean) = interactor.toggleIsPrice(boolean)
}