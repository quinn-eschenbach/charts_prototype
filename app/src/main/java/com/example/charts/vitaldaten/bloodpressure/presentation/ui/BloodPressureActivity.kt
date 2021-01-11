package com.example.charts.vitaldaten.bloodpressure.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.charts.LineChartSetup
import com.example.charts.vitaldaten.bloodpressure.data.*
import com.example.charts.vitaldaten.bloodpressure.presentation.BloodPressureViewModel
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.di.DaggerActivityComponent
import com.github.mikephil.charting.data.LineData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_bloodpressure.*
import java.lang.Exception
import javax.inject.Inject


class BloodPressureActivity :AppCompatActivity() {
    lateinit var profile :Profile
    private val composible by lazy { CompositeDisposable() }
    private val component = DaggerActivityComponent.create()
    private var highlight: Highlight = Highlight.ALL
    @Inject
    lateinit var viewModel: BloodPressureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloodpressure)
        component.inject(this)
        profile = intent.getSerializableExtra("PROFILE") as Profile
        viewModel.setContext(this)
        viewModel.fetchBloodPressureStates().subscribe{
            renderStates(it)
        }.also {
            composible.add(it)
        }
        viewModel.getProfile()
        viewModel.getData()

        chart_blood.setOnClickListener {
            setupTexts()
        }
       btn_dia.setOnClickListener {
            highlight = if(highlight == Highlight.DIA){
                Highlight.ALL
            }else{
                Highlight.DIA
            }
           viewModel.clickHighlight(highlight)
           viewModel.getData()
       }
        btn_sys.setOnClickListener {
            highlight = if(highlight == Highlight.SYS){
                Highlight.ALL
            }else{
                Highlight.SYS
            }
            viewModel.clickHighlight(highlight)
            viewModel.getData()
        }
        btn_pulse.setOnClickListener {
            highlight = if(highlight == Highlight.PULSE){
                Highlight.ALL
            }else{
                Highlight.PULSE
            }
            viewModel.clickHighlight(highlight)
            viewModel.getData()
        }
    }

    private fun renderStates(state: BloodPressureState){
        when(state){
            is UpdateChart -> updateChart(state.data)
            is UpdateProfile -> profile = state.profile
        }
    }

    private fun updateChart(data: LineData){
        val chart = LineChartSetup.getLineChart(chart_blood,data,profile,highlight)
        chart.invalidate()
        setupTexts()
    }

    fun setupTexts(){
        var averageSysWeek = 0
        var averageDiaWeek = 0
        var averagePulsWeek = 0
        var averageSysMonth = 0
        var averageDiaMonth = 0
        var averagePulsMonth = 0
        var averageSysYear = 0
        var averageDiaYear = 0
        var averagePulsYear = 0

        val dataSys = chart_blood.data.getDataSetByLabel("Systolisch", true)
        val dataDia = chart_blood.data.getDataSetByLabel("Diastolisch", true)
        val dataPuls = chart_blood.data.getDataSetByLabel("Puls", true)

        try {
            for(i in ((dataDia.xMax.toInt() - 7)..dataDia.xMax.toInt())){
                averageDiaWeek += dataDia.getEntryForXValue(i.toFloat(),1f).y.toInt()
                averageSysWeek += dataSys.getEntryForXValue(i.toFloat(),1f).y.toInt()
                averagePulsWeek += dataPuls.getEntryForXValue(i.toFloat(),1f).y.toInt()
            }
            averageSysWeek /= 7
            averageDiaWeek /= 7
            averagePulsWeek /= 7

            for(i in ((dataDia.xMax.toInt() - 30)..dataDia.xMax.toInt())){
                averageDiaMonth += dataDia.getEntryForXValue(i.toFloat(),1f).y.toInt()
                averageSysMonth += dataSys.getEntryForXValue(i.toFloat(),1f).y.toInt()
                averagePulsMonth += dataPuls.getEntryForXValue(i.toFloat(),1f).y.toInt()
            }
            averageSysMonth /= 30
            averageDiaMonth /= 30
            averagePulsMonth /= 30

            for(i in ((dataDia.xMax.toInt() - 365)..dataDia.xMax.toInt())){
                averageDiaYear += dataDia.getEntryForXValue(i.toFloat(),1f).y.toInt()
                averageSysYear += dataSys.getEntryForXValue(i.toFloat(),1f).y.toInt()
                averagePulsYear += dataPuls.getEntryForXValue(i.toFloat(),1f).y.toInt()
            }
            averageSysYear /= 365
            averageDiaYear /= 365
            averagePulsYear /= 365


        }catch (e: Exception){

        }
        sys_average_week.text = "Ø Letzte Woche: $averageSysWeek"
        sys_average_month.text = "Ø Letzter Monat: $averageSysMonth"
        sys_average_year.text = "Ø Letztes Jahr: $averageSysYear"

        dia_average_week.text = "Ø Letzte Woche: $averageDiaWeek"
        dia_average_month.text = "Ø Letzter Monat: $averageDiaMonth"
        dia_average_year.text = "Ø Letztes Jahr: $averageDiaYear"

        pulse_average_week.text = "Ø Letzte Woche: $averagePulsWeek"
        pulse_average_month.text = "Ø Letzter Monat: $averagePulsMonth"
        pulse_average_year.text = "Ø Letztes Jahr: $averagePulsYear"
    }
}

