package com.example.charts.vitaldaten

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.vitaldaten.bloodpressure.presentation.ui.BloodPressureActivity
import com.example.charts.vitaldaten.bloodsugar.BloodSugarActivity
import com.example.charts.vitaldaten.data.DataSetup
import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.settings.SettingsActivity
import com.example.charts.vitaldaten.weight.presentation.ui.WeightActivity
import kotlinx.android.synthetic.main.activity_vitaldaten.*
import kotlinx.android.synthetic.main.popup_profile.*

class VitalActivity: AppCompatActivity() {
    private val profiles = DataSetup.profiles
    var currentProfile : Profile? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vitaldaten)
        showDialog()
        tile_weight.setOnClickListener {
            if(currentProfile != null){
                val intent = Intent(this, WeightActivity::class.java)
                intent.putExtra("PROFILE_ID", currentProfile!!.id)
                startActivity(intent)
            }
            else{
                showDialog()
            }
        }
        tile_pressure.setOnClickListener {
            if(currentProfile != null){
                val intent = Intent(this, BloodPressureActivity::class.java)
                intent.putExtra("PROFILE_ID", currentProfile!!.id)
                startActivity(intent)
            }
            else{
                showDialog()
            }

        }
        tile_settings.setOnClickListener {
            if(currentProfile != null){
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("PROFILE_ID", currentProfile!!.id)
                startActivity(intent)
            }
            else{
                showDialog()
            }
        }
        tile_sugar.setOnClickListener {
            if(currentProfile != null){
                val intent = Intent(this, BloodSugarActivity::class.java)
                //intent.putExtra("PROFILE_ID", currentProfile!!.id)
                startActivity(intent)
            }
            else{
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popup_profile)
        dialog.show()
        profiles.forEach {profile ->
            val button = Button(this)
            button.text = profile.name
            button.setOnClickListener {
                currentProfile = profile
                txt_welcome.text = "Hallo ${profile.name}"
                dialog.hide()
            }
            dialog.popup_profile.addView(button)
        }
    }
}