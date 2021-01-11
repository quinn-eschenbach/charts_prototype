package com.example.charts.vitaldaten.weight.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.charts.LineChartSetup
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.di.DaggerActivityComponent
import com.example.charts.vitaldaten.weight.data.UpdateChart
import com.example.charts.vitaldaten.weight.data.UpdateProfile
import com.example.charts.vitaldaten.weight.data.Weight
import com.example.charts.vitaldaten.weight.data.WeightState
import com.example.charts.vitaldaten.weight.presentation.WeightViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_weight.*
import javax.inject.Inject


class WeightActivity : AppCompatActivity() {
    lateinit var profile: Profile
    private val composible by lazy { CompositeDisposable() }
    private val component = DaggerActivityComponent.create()
    @Inject
    lateinit var viewModel : WeightViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        component.inject(this)

        viewModel.fetchStates().subscribe {
            renderStates(it)
        }.also {
            composible.add(it)
        }
        viewModel.getProfile()
        viewModel.getData()
    }

    private fun setupChart(data: List<Weight>) {
        val lineData = LineChartSetup.prepareWeightSet(data)
        val chart = LineChartSetup.getLineChart(chart_weight,data,lineData,
             profile, profile.targetWeight)
        chart.invalidate()
    }

    private fun renderStates(state: WeightState){
        when(state){
            is UpdateChart -> setupChart(state.data)
            is UpdateProfile -> profile = state.profile
        }
    }

    fun setupTexts() {/*
        var lastWeight = data[0]
        data.forEach {
            if (it.timeStamp > lastWeight.timeStamp) {
                lastWeight = it
            }
        }
        val bmi = lastWeight.weight.div((currentProfile.height * currentProfile.height))
        bmi_value.text = "Ihr BMI: ${bmi?.toBigDecimal()?.setScale(1, RoundingMode.HALF_EVEN)}"
        val calendar = Calendar.getInstance()
        calendar.time = currentProfile.birthday
        bmi_evaluation.text = getBmiLevel(bmi, Year.now().value - calendar.get(Calendar.YEAR))
        when (currentProfile.targetWeight) {
            null -> target_evaluation.text = ""
            else -> {
                if (lastWeight.weight <= currentProfile.targetWeight!!) {
                    target_evaluation.text =
                        "Glückwunsch ${currentProfile.name}, Sie haben Ihr Zielgewicht erreicht"
                } else {
                    target_evaluation.text =
                        "Es fehlen nur noch ${lastWeight.weight.minus(currentProfile.targetWeight!!).toBigDecimal()?.setScale(1, RoundingMode.HALF_EVEN)}KG, weiter so!"
                }
            }
        }*/
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