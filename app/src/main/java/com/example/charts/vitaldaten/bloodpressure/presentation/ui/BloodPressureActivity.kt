package com.example.charts.vitaldaten.bloodpressure.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.charts.LineChartSetup
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressure
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressureState
import com.example.charts.vitaldaten.bloodpressure.data.UpdateChart
import com.example.charts.vitaldaten.bloodpressure.data.UpdateProfile
import com.example.charts.vitaldaten.bloodpressure.presentation.BloodPressureViewModel
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.di.DaggerActivityComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_bloodpressure.*
import javax.inject.Inject


class BloodPressureActivity :AppCompatActivity() {
    lateinit var profile: Profile
    private val composible by lazy { CompositeDisposable() }
    private val component = DaggerActivityComponent.create()
    @Inject
    lateinit var viewModel: BloodPressureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloodpressure)
        component.inject(this)

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
       /* btn_dia.setOnClickListener {
            if(currentHIGHLIGHT != HIGHLIGHT.DIA){
                currentHIGHLIGHT =
                    HIGHLIGHT.DIA
                setupChart()
            }else{
                currentHIGHLIGHT =
                    HIGHLIGHT.ALL
                setupChart()
            }
        }
        btn_sys.setOnClickListener {
            if(currentHIGHLIGHT != HIGHLIGHT.SYS){
                currentHIGHLIGHT =
                    HIGHLIGHT.SYS
                setupChart()
            }else{
                currentHIGHLIGHT =
                    HIGHLIGHT.ALL
                setupChart()
            }
        }
        btn_pulse.setOnClickListener {
            if(currentHIGHLIGHT != HIGHLIGHT.PULSE){
                currentHIGHLIGHT =
                    HIGHLIGHT.PULSE
                setupChart()
            }else{
                currentHIGHLIGHT =
                    HIGHLIGHT.ALL
                setupChart()
            }
        }*/
    }

    private fun renderStates(state: BloodPressureState){
        when(state){
            is UpdateChart -> updateChart(state.data)
            is UpdateProfile -> profile = state.profile
        }
    }

    private fun updateChart(data: List<BloodPressure>){

        val lineData = LineChartSetup.prepareBloodPressureData(data, this)
        val chart = LineChartSetup.getLineChart(chart_blood,data,lineData,profile, null)
        chart.invalidate()
        setupTexts()
    }




    fun setupTexts(){
       var averageSys = 0
        var averageDia = 0
        var averagePuls = 0
        val chart = chart_blood

        val dataSys = chart_blood.data.getDataSetByLabel("Systolisch", true)
        val dataDia = chart_blood.data.getDataSetByLabel("Diastolisch", true)
        val dataPuls = chart_blood.data.getDataSetByLabel("Puls", true)
        

        val x = dataSys.getEntryForXValue(chart.lowestVisibleX.toInt().toFloat(),1f)
        val q = dataSys.getEntryForXValue(chart.highestVisibleX.toInt().toFloat(),1f)
        val o = ((chart.lowestVisibleX.toInt())..chart.highestVisibleX.toInt())
        Log.d("q", "high: $q")
        Log.d("q", "low:  $x")
        Log.d("q", "low:  $o")

        /*for(i in (chart.lowestVisibleX.toInt())..chart.highestVisibleX.toInt()){
            averageDia += dataDia.getEntryForXValue(i.toFloat(),1f).y.toInt()
            averageSys += dataSys.getEntryForXValue(i.toFloat(),1f).y.toInt()
            averagePuls += dataPuls.getEntryForXValue(i.toFloat(),1f).y.toInt()
        }*/



        /*data.sortedByDescending {
            it.timeStamp
        }
        val lastMonth = data.slice(0..31)
        lastMonth.forEach {
            averageDia += it.dia.toInt()
            averageSys += it.sys.toInt()
            averagePlus += it.pulse.toInt()
        }*/
        averageDia /= chart.highestVisibleX.toInt()-(chart.lowestVisibleX.toInt())
        averageSys /= chart.highestVisibleX.toInt()-(chart.lowestVisibleX.toInt())
        averagePuls /= chart.visibleXRange.toInt()


        sys_average.text = "Ø  $averageSys"
        //sys_min.text = "Kleinster Wert: ${lastMonth.sortedBy { it.sys }[0].sys}"
        //sys_max.text = "Größter Wert: ${lastMonth.sortedByDescending { it.sys }[0].sys}"
        dia_average.text = "Ø  $averageDia"
        //dia_min.text = "Kleinster Wert: ${lastMonth.sortedBy { it.dia }[0].dia}"
        //dia_max.text = "Größter Wert: ${lastMonth.sortedByDescending { it.dia }[0].dia}"
        pulse_average.text = "Ø  $averagePuls"
        //pulse_min.text = "Kleinster Wert: ${lastMonth.sortedBy { it.pulse }[0].pulse}"
        //pulse_max.text = "Größter Wert: ${lastMonth.sortedByDescending { it.pulse }[0].pulse}"

    }
}

