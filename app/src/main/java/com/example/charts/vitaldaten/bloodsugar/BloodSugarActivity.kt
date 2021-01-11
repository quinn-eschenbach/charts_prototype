package com.example.charts.vitaldaten.bloodsugar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.charts.R
import com.example.charts.vitaldaten.data.DataSetup
import kotlinx.android.synthetic.main.activity_bloodsugar.*


class BloodSugarActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloodsugar)
        val recyclerView = recycler_sugar
        val adapter =
            SugarAdapter(
                DataSetup.getSugar(
                    1
                )
            )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}



