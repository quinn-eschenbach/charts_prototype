package com.example.charts.vitaldaten.weight.presentation

import com.example.charts.vitaldaten.weight.domain.WeightInteractor
import javax.inject.Inject

class WeightViewModel @Inject constructor(
     private val interactor: WeightInteractor
) {
    fun fetchStates() = interactor.getWeightState()

    fun getData() = interactor.getData()

    fun getProfile() = interactor.getProfile()
}