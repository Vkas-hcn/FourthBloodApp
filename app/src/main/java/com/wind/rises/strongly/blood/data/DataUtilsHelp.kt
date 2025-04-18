package com.wind.rises.strongly.blood.data

import androidx.annotation.Keep
import com.wind.rises.strongly.blood.show.App
import com.google.gson.Gson
import java.io.File

@Keep
data class LocalStorageData(
    var pressureJson: String = "",
    var sugarJson: String = "",
    var sugarUnit: String = ""
)
@Keep
data class SugarBean(
    val numL: Double,
    val numDL: Double,
    val state: TBSUtils.BloodSugarStatus,
    val currentState: TBSUtils.CurrentState,
    val date: String,
    val id:String,
)
@Keep
data class PressureBean(
    val systolic: Int,
    val diatonic: Int,
    val pultonic:Int,
    val date: String,
    val id: String
)
object DataUtilsHelp {
    private const val FILE_NAME = "local_data.json"
    private val context by lazy { App.appComponent }
    private val filePath: String get() = context.filesDir.path + "/" + FILE_NAME
    private var cachedData: LocalStorageData? = null

    private fun readData(): LocalStorageData {
        if (cachedData != null) return cachedData!!
        val file = File(filePath)
        if (!file.exists()) {
            cachedData = LocalStorageData()
            return cachedData!!
        }
        val json = file.readText()
        cachedData = Gson().fromJson(json, LocalStorageData::class.java)
        return cachedData!!
    }

    private fun saveData(data: LocalStorageData) {
        cachedData = data
        val json = Gson().toJson(data)
        File(filePath).writeText(json)
    }
    var pressureJson: String
        get() = readData().pressureJson
        set(value) {
            val current = readData()
            current.pressureJson = value
            saveData(current)
        }

    var sugarJson: String
        get() = readData().sugarJson
        set(value) {
            val current = readData()
            current.sugarJson = value
            saveData(current)
        }

    // 其他字段（pressureJson、sugarJson、sugarUnit）同理
    var sugarUnit: String
        get() = readData().sugarUnit
        set(value) {
            val current = readData()
            current.sugarUnit = value
            saveData(current)
        }
}