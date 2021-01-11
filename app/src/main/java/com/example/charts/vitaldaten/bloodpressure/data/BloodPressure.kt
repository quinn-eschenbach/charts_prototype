package com.example.charts.vitaldaten.bloodpressure.data
import com.example.charts.vitaldaten.data.ILineValues

class BloodPressure(
    val sys: Float,
    val dia: Float,
    val pulse: Float,
    override val profileId: Int?,
    override val timeStamp: Long
) : ILineValues
