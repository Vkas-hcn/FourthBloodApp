package com.blue.colored.frog.flowing.light.thirdbloodpressure.key

import androidx.annotation.Keep

@Keep
data class PressureBean(
    val systolic: Int,
    val diatonic: Int,
    val pultonic:Int,
    val date: String,
    val remark:String,
    val id: String
)
