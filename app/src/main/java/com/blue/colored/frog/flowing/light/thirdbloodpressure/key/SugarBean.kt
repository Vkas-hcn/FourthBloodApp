package com.blue.colored.frog.flowing.light.thirdbloodpressure.key

import androidx.annotation.Keep

@Keep
data class SugarBean(
    val numL: Double,
    val numDL: Double,
    val state: TBSUtils.BloodSugarStatus,
    val currentState: TBSUtils.CurrentState,
    val date: String,
    val remarks: String,
    val id:String,
)
