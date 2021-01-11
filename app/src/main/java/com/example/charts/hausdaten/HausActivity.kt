package com.example.charts.hausdaten

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.hausdaten.abrechnung.AbrechnungActivity
import com.example.charts.hausdaten.water.presentation.ui.WaterActivity
import kotlinx.android.synthetic.main.activity_hausdaten.*

class HausActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hausdaten)
        btn_wasser.setOnClickListener {
            val intent = Intent(this, WaterActivity::class.java)
            startActivity(intent)
        }
        btn_abrechnung.setOnClickListener {
            val intent = Intent(this, AbrechnungActivity::class.java)
            startActivity(intent)
        }
    }

}