package com.example.charts.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charts.R
import com.example.charts.hausdaten.HausActivity
import com.example.charts.vitaldaten.VitalActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    btn_hausdaten.setOnClickListener {
      val intent = Intent(this, HausActivity::class.java)
      startActivity(intent)
    }
    btn_vitaldaten.setOnClickListener {
      val intent = Intent(this, VitalActivity::class.java)
      startActivity(intent)
    }
  }
}

