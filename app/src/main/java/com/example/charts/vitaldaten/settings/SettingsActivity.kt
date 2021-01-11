package com.example.charts.vitaldaten.settings

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.R
import com.example.charts.vitaldaten.data.DataSetup
import com.example.charts.vitaldaten.data.Profile
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.popup_datepicker.*
import java.time.Instant
import java.util.*

class SettingsActivity:AppCompatActivity() {
    private lateinit var currentProfile: Profile
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        currentProfile = intent.getSerializableExtra("PROFILE") as Profile
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setupSettings()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupSettings(){
        edit_name.text = SpannableStringBuilder(currentProfile.name)

        range_sys.apply {
            setProgress(currentProfile.minSys, currentProfile.maxSys)
            setIndicatorTextDecimalFormat("0")

            setOnRangeChangedListener(object : OnRangeChangedListener{
                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    currentProfile.minSys = leftValue.toInt().toFloat()
                    currentProfile.maxSys = rightValue.toInt().toFloat()
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}
            })
        }

        range_dia.apply {
            setIndicatorTextDecimalFormat("0")
            setProgress(currentProfile.minDia, currentProfile.maxDia)

            setOnRangeChangedListener(object :OnRangeChangedListener{
                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    currentProfile.minDia = leftValue.toInt().toFloat()
                    currentProfile.maxDia = rightValue.toInt().toFloat()
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}
            })
        }


        range_pulse.apply {
            setIndicatorTextDecimalFormat("0")
            setProgress(currentProfile.minPulse, currentProfile.maxPulse)

            setOnRangeChangedListener(object :OnRangeChangedListener{
                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    currentProfile.minPulse = leftValue.toInt().toFloat()
                    currentProfile.maxPulse = rightValue.toInt().toFloat()
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}
            })
        }


        slider_height.apply {
            setIndicatorTextDecimalFormat("0")
            setProgress(currentProfile.height * 100)

            setOnRangeChangedListener(object :OnRangeChangedListener{
                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    currentProfile.height = leftValue.toInt().toFloat() / 100
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}
            })
        }


        slider_target_weight.setIndicatorTextDecimalFormat("0")
            if(currentProfile.targetWeight != null){
                toggle_target_weight.isChecked = true
                slider_target_weight.setProgress(currentProfile.targetWeight!!)
            }else{
                toggle_target_weight.isChecked= false
            }

        slider_target_weight.setOnRangeChangedListener(object :OnRangeChangedListener{
                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    if(toggle_target_weight.isChecked){
                        currentProfile.targetWeight = leftValue.toInt().toFloat()
                    }
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}
            })


        toggle_target_weight.setOnCheckedChangeListener { buttonView, isChecked ->
            currentProfile.targetWeight = if(isChecked){
                slider_target_weight.leftSeekBar.progress.toInt().toFloat()
            }else{
                null
            }
        }


        val date = Calendar.getInstance()
        date.time = currentProfile.birthday
        btn_birthday.text = "${date.get(Calendar.DAY_OF_MONTH)}.${date.get(Calendar.MONTH) + 1}.${date.get(Calendar.YEAR)}"
        btn_birthday.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.popup_datepicker)
            dialog.datePicker.updateDate(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
            val btnCancel = Button(this)
            btnCancel.text = "zurück"
            val btnOk = Button(this)
            btnOk.text = "speichern"
            dialog.layout_btns_popup.addView(btnCancel)
            dialog.layout_btns_popup.addView(btnOk)
            btnCancel.setOnClickListener {
                dialog.hide()
            }
            btnOk.setOnClickListener {
                val datePicker = dialog.datePicker
                currentProfile.birthday = Date.from(Instant.parse(dateformatter(datePicker.dayOfMonth,datePicker.month,datePicker.year)))
                dialog.hide()
                btn_birthday.text = "${date.get(Calendar.DAY_OF_MONTH)}.${date.get(Calendar.MONTH) + 1}.${date.get(Calendar.YEAR)}"
            }
        }

        btn_save_profile.setOnClickListener {
            currentProfile.name = edit_name.text.toString()
           DataSetup.profiles.find {
               it.id == currentProfile.id
           }.also {
              DataSetup.profiles.remove(it)
              DataSetup.profiles.add(currentProfile)
           }
        }
    }

    fun dateformatter(day: Int, month:Int, year: Int ):String{
        val formattedMonth = if("${month+1}".length < 2){
            "0${month+1}"
        }else{
            "${month+1}"
        }
        val formattedDay = if("$day".length < 2){
            "0$day"
        }else{
            "$day"
        }
        return "$year-$formattedMonth-${formattedDay}T00:00:00Z"
    }
}