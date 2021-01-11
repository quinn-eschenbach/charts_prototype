package com.example.charts.vitaldaten.bloodsugar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.charts.R
import com.example.charts.charts.LineChartSetup
import com.example.charts.vitaldaten.bloodsugar.BloodSugar
import com.example.charts.vitaldaten.data.DataSetup
import com.github.mikephil.charting.charts.LineChart
import kotlinx.android.synthetic.main.item_blood_sugar.view.*
import java.util.*

class SugarAdapter(private val data: List<BloodSugar>): RecyclerView.Adapter<SugarAdapter.ViewHolder>() {
    private var preparedData = mutableListOf<List<BloodSugar>>()

    init{
        val dates = mutableListOf<Int>()
        data.sortedBy {
            it.timeStamp
        }
        data.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = Date(it.timeStamp)
            if(!dates.contains(calendar.get(Calendar.DAY_OF_YEAR))){
                dates.add(calendar.get(Calendar.DAY_OF_YEAR))
            }
        }
        dates.forEach { date ->
            preparedData.add(
                data.filter {
                    val calendar = Calendar.getInstance()
                    calendar.time = Date(it.timeStamp)
                    calendar.get(Calendar.DAY_OF_YEAR) == date
                }
            )
        }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val textAvrg: TextView =  view.txt_avrg_sugar
        val textDate: TextView =  view.txt_date_recycler
        val chart: LineChart = view.chart_bloodsugar
        val context: Context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blood_sugar, parent,false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount() = preparedData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when{
            position % 2 == 1 -> holder.view.background = ContextCompat.getDrawable(holder.context,R.drawable.gray_background)
        }

        var average = 0f
        preparedData[position].forEach {
            average += it.bloodSugar
        }
        average /= preparedData[position].size
        holder.textAvrg.text = "Ø${average.toInt()}"
        val profile =DataSetup.profiles.find {
           it.id == data[0].profileId
        }
        if (profile != null) {
            when{
                average < profile.minBloodSugar                               || average > profile.maxBloodSugar                                -> holder.textAvrg.background = ContextCompat.getDrawable(holder.context, R.drawable.shape_round_red)
                average < profile.minBloodSugar + profile.warnRangeBloodSugar || average > profile.maxBloodSugar - profile.warnRangeBloodSugar  -> holder.textAvrg.background = ContextCompat.getDrawable(holder.context, R.drawable.shape_round_yellow)
                else -> holder.textAvrg.background = ContextCompat.getDrawable(holder.context, R.drawable.shape_round_green)
            }
        }


        val calendar = Calendar.getInstance()
        calendar.time = Date(preparedData[position][0].timeStamp)
        holder.textDate.text = "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH )+1}"

        val chart = LineChartSetup.getSugarLineChart(holder.chart)
        chart.data = LineChartSetup.prepareSugarSet(preparedData[position],DataSetup.profiles[0], holder.context)
        chart.invalidate()

    }

}