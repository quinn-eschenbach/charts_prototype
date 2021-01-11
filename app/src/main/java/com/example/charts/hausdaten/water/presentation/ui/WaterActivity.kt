package com.example.charts.hausdaten.water.presentation.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.charts.BarChartSetup
import com.example.charts.hausdaten.water.presentation.WaterViewModel
import com.example.charts.hausdaten.di.DaggerActivityComponent
import com.example.charts.hausdaten.water.data.UpdateWaterChart
import com.example.charts.hausdaten.water.data.WaterState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_wasser.*
import javax.inject.Inject

class WaterActivity:AppCompatActivity() {
    private val composible by lazy { CompositeDisposable() }
    private val component = DaggerActivityComponent.create()

    private var isPrice = false
    @Inject
    lateinit var viewModel: WaterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wasser)
        component.inject(this)

        viewModel.fetchWaterStates().subscribe{
            renderStates(it)
        }.also {
            composible.add(it)
        }
        viewModel.getMonthly()
       setupUi()
    }

    private fun setupUi(){
        btn_yearly.setOnClickListener {
            viewModel.getYearly()
        }
        btn_monthly.setOnClickListener {
            viewModel.getMonthly()
        }
        btn_weekly.setOnClickListener {
            viewModel.getWeekly()
        }

        val spinnerConsumption = spinner_consumption
        val spinnerReference = spinner_reference
        val spinnerLastYear = spinner_last_year

        spinnerConsumption.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.colors,
            R.layout.support_simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }
        spinnerConsumption.setSelection(0)
        spinnerConsumption.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.changeColor(0, positionToColor(position))
            }
        })


        spinnerReference.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.colors,
            R.layout.support_simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }
        spinnerReference.setSelection(1)
        spinnerReference.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.changeColor(1, positionToColor(position))
            }
        })


        spinnerLastYear.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.colors,
            R.layout.support_simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }
        spinnerLastYear.setSelection(2)
        spinnerLastYear.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.changeColor(2, positionToColor(position))
            }
        })



        switch_reference.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleSet(1, isChecked)
        }
        switch_last_year.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleSet(2, isChecked)
        }

        btn_toggle_price.setOnClickListener {
            if(isPrice){
                viewModel.toggleIsPrice(!isPrice)
                btn_toggle_price.text = "Kosten anzeigen"
            }
            else{
                viewModel.toggleIsPrice(!isPrice)
                btn_toggle_price.text = "Verbrauch anzeigen"
            }
            isPrice = !isPrice
        }
    }

    private fun renderStates(state: WaterState){
        when(state){
            is UpdateWaterChart -> BarChartSetup.setupBarChart(chart_wasser, state.data)
        }
    }

    fun positionToColor(int: Int): Int {
        return when(int){
            0 -> Color.BLUE
            1 -> Color.RED
            2 -> Color.GREEN
            3 -> Color.YELLOW
            4 -> Color.MAGENTA
            else -> Color.CYAN
        }
    }
}