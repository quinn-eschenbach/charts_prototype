package com.example.charts.vitaldaten.bloodpressure.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.vitaldaten.bloodpressure.data.AverageMeasures
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState
import com.example.charts.vitaldaten.bloodpressure.data.Highlight.*
import com.example.charts.vitaldaten.bloodpressure.presentation.BloodPressureActions.*
import com.example.charts.vitaldaten.bloodpressure.presentation.BloodPressureViewModel
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.di.ActivityComponent
import com.example.charts.vitaldaten.di.ActivityModule
import com.example.charts.vitaldaten.di.ChartsApp
import com.github.mikephil.charting.charts.LineChart
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_bloodpressure.*
import javax.inject.Inject


class BloodPressureActivity : AppCompatActivity() {
    lateinit var profile: Profile
    private val disposable by lazy { CompositeDisposable() }
    private val component by lazy { createComponent() }
    private var shouldDisplayAll = false

    @Inject
    lateinit var viewModel: BloodPressureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloodpressure)
        component.inject(this)
        profile = intent.getSerializableExtra("PROFILE") as Profile

        viewModel
            .fetchBloodPressureStates()
            .subscribe(::renderStates)
            .also { disposable.add(it) }

        viewModel.sendActions(HighlightAll(incomingChart = chart_blood, profile = profile))

        viewModel.getProfile()


        btn_dia.setOnClickListener {
            shouldDisplayAll = !shouldDisplayAll
            if (shouldDisplayAll) {
                viewModel.sendActions(HighlightAll(chart_blood, profile))

            } else {
                viewModel.sendActions(HighlightDia(chart_blood, profile, DIA))
            }
        }

        btn_sys.setOnClickListener {
            shouldDisplayAll = !shouldDisplayAll
            if (shouldDisplayAll) {
                viewModel.sendActions(HighlightAll(chart_blood, profile))

            } else {
                viewModel.sendActions(HighlightSys(chart_blood, profile, SYS))
            }
        }

        btn_pulse.setOnClickListener {
            shouldDisplayAll = !shouldDisplayAll
            if (shouldDisplayAll) {
                viewModel.sendActions(HighlightAll(chart_blood, profile))

            } else {
                viewModel.sendActions(HighlightPulse(chart_blood, profile, PULSE))
            }
        }
    }

    private fun renderStates(state: BloodPressureState) {
        when (state) {
            is BloodPressureState.UpdateChart -> updateChart(state.data, state.averageMap)
            is BloodPressureState.UpdateProfile -> profile = state.profile
        }
    }

    private fun updateChart(data: LineChart, averageMap: AverageMeasures) {
        data.invalidate()
        setupTexts(averageMap)
    }

    private fun setupTexts(averageMap: AverageMeasures) {
        with(averageMap){
            sys_average_week.text = "Ø Letzte Woche: $averageSysWeek"
            sys_average_month.text = "Ø Letzter Monat: $averageSysMonth"
            sys_average_year.text = "Ø Letztes Jahr: $averageSysYear"

            dia_average_week.text = "Ø Letzte Woche: $averageDiaWeek"
            dia_average_month.text = "Ø Letzter Monat: $averageDiaMonth"
            dia_average_year.text = "Ø Letztes Jahr: $averageDiaYear"

            pulse_average_week.text = "Ø Letzte Woche: $averagePulseWeek"
            pulse_average_month.text = "Ø Letzter Monat: $averagePulseMonth"
            pulse_average_year.text = "Ø Letztes Jahr: $averagePulseYear"
        }
    }

    private fun createComponent(): ActivityComponent {
        val application = ChartsApp::class.java.cast(application)
        val component = application!!.getComponent()
        return component.createActivityComponent(ActivityModule(this))
    }
}

