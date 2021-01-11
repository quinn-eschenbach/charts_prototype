package com.example.charts.hausdaten.abrechnung

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnSliderTouchListener
import kotlinx.android.synthetic.main.activity_abrechnung.*
import java.math.RoundingMode

class AbrechnungActivity: AppCompatActivity() {
    private val pieDataYear = mapOf("Strom" to 220.3f, "Wasser" to 160.2f, "Wärme" to 103.55f, "Abfall" to 50.6f, "Sonstiges" to 70.5f)
    val pieDataMonth = mapOf("Strom" to 20.3f, "Wasser" to 10.2f, "Wärme" to 16f, "Abfall" to 4.7f, "Sonstiges" to 1.75f)
    private lateinit var months : MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        months =  resources.getStringArray(R.array.months).toMutableList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abrechnung)
        setupPieChart(pieDataYear, "Total")

        btn_abrechnung_jahr.setOnClickListener {
            setupPieChart(pieDataYear, "Total")
        }

        slider_monat.addOnChangeListener { _, value, _ ->

            val monat = months[value.toInt() - 1]
            setupPieChart(pieDataMonth, monat)
        }
    }

    private fun setupPieChart(data : Map<String, Float>, labelMitte : String){
        val entries = ArrayList<PieEntry>()
        var totalCost = 0f
        data.map {
            totalCost += it.value
        }
        data.map {
            entries.add(PieEntry(it.value, it.key))
        }

        val set = PieDataSet(entries, "2020")
        set.sliceSpace = 3f
        set.valueTextColor = Color.WHITE
        set.colors = ColorTemplate.COLORFUL_COLORS.map {
            it
        }
        set.selectionShift = 5f

        val dataSet = PieData(set)
        chart_abrechnung.centerText = "$labelMitte: ${totalCost.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)}€"
        chart_abrechnung.animateY(1000)
        chart_abrechnung.data = dataSet
        chart_abrechnung.setUsePercentValues(false)
        chart_abrechnung.description.isEnabled = false
        chart_abrechnung.invalidate()
    }

}