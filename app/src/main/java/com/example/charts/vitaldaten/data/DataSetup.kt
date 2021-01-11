package com.example.charts.vitaldaten.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.charts.vitaldaten.bloodpressure.data.BloodPressure
import com.example.charts.vitaldaten.bloodsugar.BloodSugar
import com.example.charts.vitaldaten.weight.data.Weight
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

object DataSetup {

    @RequiresApi(Build.VERSION_CODES.O)
    var profiles = mutableListOf(
        Profile(
            1,
            "Erna",
            1.68f,
            Date.from(Instant.parse("1953-12-16T00:00:00Z")),
            60f,
            110f,
            130f,
            80f,
            90f,
            60f,
            80f,
            100f,
            140f,
            10f
        ),
        Profile(
            2,
            "Kasimir",
            1.75f,
            Date.from(Instant.parse("1948-11-20T00:00:00Z")),
            80f,
            110f,
            130f,
            80f,
            90f,
            60f,
            80f,
            100f,
            140f,
            10f
        ),
        Profile(
            3,
            "Gisela",
            1.52f,
            Date.from(Instant.parse("1933-03-22T00:00:00Z")),
            null,
            110f,
            130f,
            80f,
            90f,
            60f,
            80f,
            100f,
            140f,
            10f
        )
    )

    fun getWeight(id: Int, range: Int?): List<Weight>{
        val range = range ?: 60
        val list = mutableListOf<Weight>()
        for (i in 1..900){
            val date =  (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(i.toLong()))
            list.add(
                Weight(
                    weight = (range + i * 0.1 ).toFloat(),
                    profileId = id,
                    timeStamp = date
                )
            )
        }
        return list
    }

    fun getPressure(id: Int): List<BloodPressure>{
        val list = mutableListOf<BloodPressure>()
        for (i in 1..500){
            val date =  (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(i.toLong()) * (1..50).random())
            list.add(
                BloodPressure(
                    sys = (110..130).random().toFloat(),
                    dia = (80..90).random().toFloat(),
                    pulse = (60..80).random().toFloat(),
                    timeStamp = date,
                    profileId = id
                )
            )
        }
        return list
    }

    fun getSugar(id:Int): MutableList<BloodSugar> {
        val list = mutableListOf<BloodSugar>()
        for(i in 1..20){
            val date =  (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(i.toLong()))
            list.add(
                BloodSugar(
                    bloodSugar = (90..160).random().toFloat(),
                    profileId = id,
                    timeStamp = date - 3600000 * (1..6).random()
                )
            )
            list.add(
                BloodSugar(
                    bloodSugar = (90..160).random().toFloat(),
                    profileId = id,
                    timeStamp = date
                )
            )
            list.add(
                BloodSugar(
                    bloodSugar = (90..160).random().toFloat(),
                    profileId = id,
                    timeStamp = date + 3600000 * (1..6).random()
                )
            )
        }
        return list
    }
}