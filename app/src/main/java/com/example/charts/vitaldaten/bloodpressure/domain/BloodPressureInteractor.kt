package com.example.charts.vitaldaten.bloodpressure.domain

import com.example.charts.vitaldaten.bloodpressure.data.AverageMeasures
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureRepository
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState.UpdateChart
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState.UpdateProfile
import com.example.charts.vitaldaten.bloodpressure.presentation.BloodPressureActions
import com.example.charts.vitaldaten.bloodpressure.presentation.ui.BloodPressureEvents
import com.example.charts.vitaldaten.data.DataSetup
import com.github.mikephil.charting.charts.LineChart
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class BloodPressureInteractor @Inject constructor(private val repository: BloodPressureRepository) :
    BloodPressureEvents {

    var averageSysWeek = 0
    var averageDiaWeek = 0
    var averagePulsWeek = 0
    var averageSysMonth = 0
    var averageDiaMonth = 0
    var averagePulsMonth = 0
    var averageSysYear = 0
    var averageDiaYear = 0
    var averagePulsYear = 0

    private val stateObservable = PublishSubject.create<BloodPressureState>()

    fun getBloodPressureState(): Observable<BloodPressureState> {
        return stateObservable
    }

    fun getProfile() {
        stateObservable.onNext(UpdateProfile(DataSetup.profiles[0]))
    }

    override fun sendActions(actions: BloodPressureActions) {
        when (actions) {
            is BloodPressureActions.HighlightAll -> {
                stateObservable.onNext(
                    UpdateChart(
                        repository.getLineChart(
                            actions.incomingChart,
                            actions.profile,
                            actions.highlight
                        ),
                        calculateTheAverage(actions.incomingChart)
                    )
                )
            }
            is BloodPressureActions.HighlightSys -> {
                stateObservable.onNext(
                    UpdateChart(
                        repository.getLineChart(
                            actions.incomingChart,
                            actions.profile,
                            actions.highlight
                        ),
                        calculateTheAverage(actions.incomingChart)
                    )
                )
            }
            is BloodPressureActions.HighlightDia -> {
                stateObservable.onNext(
                    UpdateChart(
                        repository.getLineChart(
                            actions.incomingChart,
                            actions.profile,
                            actions.highlight
                        ),
                        calculateTheAverage(actions.incomingChart)
                    )
                )
            }
            is BloodPressureActions.HighlightPulse -> {
                stateObservable.onNext(
                    UpdateChart(
                        repository.getLineChart(
                            actions.incomingChart,
                            actions.profile,
                            actions.highlight
                        ),
                        calculateTheAverage(actions.incomingChart)
                    )
                )
            }
        }
    }

    private fun calculateTheAverage(incomingChart: LineChart): AverageMeasures {
        try {
            val dataSys = incomingChart.data.getDataSetByLabel("Systolisch", true)
            val dataDia = incomingChart.data.getDataSetByLabel("Diastolisch", true)
            val dataPulse = incomingChart.data.getDataSetByLabel("Puls", true)

            for (i in ((dataDia.xMax.toInt() - 7)..dataDia.xMax.toInt())) {
                averageDiaWeek += dataDia.getEntryForXValue(i.toFloat(), 1f).y.toInt()
                averageSysWeek += dataSys.getEntryForXValue(i.toFloat(), 1f).y.toInt()
                averagePulsWeek += dataPulse.getEntryForXValue(i.toFloat(), 1f).y.toInt()
            }

            averageSysWeek /= 7
            averageDiaWeek /= 7
            averagePulsWeek /= 7

            for (i in ((dataDia.xMax.toInt() - 30)..dataDia.xMax.toInt())) {
                averageDiaMonth += dataDia.getEntryForXValue(i.toFloat(), 1f).y.toInt()
                averageSysMonth += dataSys.getEntryForXValue(i.toFloat(), 1f).y.toInt()
                averagePulsMonth += dataPulse.getEntryForXValue(i.toFloat(), 1f).y.toInt()
            }
            averageSysMonth /= 30
            averageDiaMonth /= 30
            averagePulsMonth /= 30

            for (i in ((dataDia.xMax.toInt() - 365)..dataDia.xMax.toInt())) {
                averageDiaYear += dataDia.getEntryForXValue(i.toFloat(), 1f).y.toInt()
                averageSysYear += dataSys.getEntryForXValue(i.toFloat(), 1f).y.toInt()
                averagePulsYear += dataPulse.getEntryForXValue(i.toFloat(), 1f).y.toInt()
            }
            averageSysYear /= 365
            averageDiaYear /= 365
            averagePulsYear /= 365

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return AverageMeasures(
            averageSysWeek = averageSysWeek,
            averageSysMonth = averageSysMonth,
            averageSysYear = averageSysYear,
            averageDiaWeek = averageDiaWeek,
            averageDiaMonth = averageDiaMonth,
            averageDiaYear = averageDiaYear,
            averagePulseWeek = averagePulsWeek,
            averagePulseMonth = averagePulsMonth,
            averagePulseYear = averagePulsYear
        )
    }
}