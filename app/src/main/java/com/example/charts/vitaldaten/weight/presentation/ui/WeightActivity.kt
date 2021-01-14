package com.example.charts.vitaldaten.weight.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.charts.LineChartSetup
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.di.AppModule
import com.example.charts.vitaldaten.di.DaggerActivityComponent
import com.example.charts.vitaldaten.di.RoomModule
import com.example.charts.vitaldaten.weight.data.UpdateChart
import com.example.charts.vitaldaten.weight.data.UpdateProfile
import com.example.charts.vitaldaten.weight.data.Weight
import com.example.charts.vitaldaten.weight.data.WeightState
import com.example.charts.vitaldaten.weight.presentation.WeightViewModel
import com.github.mikephil.charting.data.LineData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_weight.*
import java.math.RoundingMode
import java.time.Year
import java.util.*
import javax.inject.Inject


class WeightActivity : AppCompatActivity() {
    lateinit var profile : Profile
    private val composible by lazy { CompositeDisposable() }
    private val component = DaggerActivityComponent.builder()
    @Inject
    lateinit var viewModel : WeightViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        component
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)

        profile = intent.getSerializableExtra("PROFILE") as Profile
        viewModel.fetchStates().subscribe {
            renderStates(it)
        }.also {
            composible.add(it)
        }
        viewModel.getData()
    }

    private fun setupChart(data: LineData) {
        val chart = LineChartSetup.getLineChart(chart_weight, data,profile, null)
        chart.invalidate()
        setupTexts()
    }

    private fun renderStates(state: WeightState){
        when(state){
            is UpdateChart -> setupChart(state.data)
            is UpdateProfile -> profile = state.profile
        }
    }

    fun setupTexts() {
        val data = chart_weight.data.dataSets[0]
        val lastWeight = data.getEntriesForXValue(data.xMax)[0].y

        val bmi = lastWeight.div((profile.height * profile.height))
        bmi_value.text = "Ihr BMI: ${bmi?.toBigDecimal()?.setScale(1, RoundingMode.HALF_EVEN)}"
        val calendar = Calendar.getInstance()
        //calendar.time = profile.birthday
        bmi_evaluation.text = getBmiLevel(bmi, Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR))
        when (profile.targetWeight) {
            null -> target_evaluation.text = ""
            else -> {
                if (lastWeight <= profile.targetWeight!!) {
                    target_evaluation.text =
                        "Glückwunsch ${profile.name}, Sie haben Ihr Zielgewicht erreicht"
                } else {
                    target_evaluation.text =
                        "Es fehlen nur noch ${lastWeight.minus(profile.targetWeight!!).toBigDecimal()?.setScale(1, RoundingMode.HALF_EVEN)}KG, weiter so!"
                }
            }
        }
    }


    private fun getBmiLevel(bmi: Float, alter: Int): String {
        val bmiRange = when (alter) {
            in 0..24 -> 19f..24f
            in 25..34 -> 20f..25f
            in 35..44 -> 21f..26f
            in 45..54 -> 22f..27f
            in 55..64 -> 23f..28f
            else -> 24f..29f
        }
        if (bmiRange.contains(bmi)) {
            return "Sie haben Normalgewicht"
        } else if (bmi < bmiRange.start) {
            return "Sie wiegen zu wenig"
        } else {
            return "Sie wiegen zu viel"
        }
    }
}