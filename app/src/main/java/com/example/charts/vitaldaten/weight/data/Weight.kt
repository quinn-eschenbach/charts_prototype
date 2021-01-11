package com.example.charts.vitaldaten.weight.data

import com.example.charts.vitaldaten.data.ILineValues

class Weight(
    val weight: Float,
    override val timeStamp: Long,
    override val profileId: Int?
) : ILineValues