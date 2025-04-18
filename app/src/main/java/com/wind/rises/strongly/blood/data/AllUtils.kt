package com.wind.rises.strongly.blood.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AllUtils {
    private val gson = Gson()

    private inline fun <reified T> getListFromJson(json: String): MutableList<T> {
        val type = object : TypeToken<MutableList<T>>() {}.type
        return runCatching { gson.fromJson<MutableList<T>>(json, type) }.getOrNull() ?: mutableListOf()
    }

    private inline fun <reified T> saveListToJson(list: MutableList<T>, jsonHolder: (String) -> Unit) {
        jsonHolder(gson.toJson(list))
    }

    fun saveSugar(pressure: SugarBean) {
        val list = getListFromJson<SugarBean>(DataUtilsHelp.sugarJson)
        list.add(pressure)
        saveListToJson(list) { DataUtilsHelp.sugarJson = it }
    }

    fun updateSugar(pressure: SugarBean) {
        val list = getListFromJson<SugarBean>(DataUtilsHelp.sugarJson)
        list.find { it.id == pressure.id }?.let {
            list[list.indexOf(it)] = pressure
            saveListToJson(list) { DataUtilsHelp.sugarJson = it }
        }
    }

    fun getSugarListData(): MutableList<SugarBean> {
        return getListFromJson<SugarBean>(DataUtilsHelp.sugarJson).sortedByDescending { it.id }.toMutableList()
    }

    fun savePressure(pressure: PressureBean) {
        val list = getListFromJson<PressureBean>(DataUtilsHelp.pressureJson)
        list.add(pressure)
        saveListToJson(list) { DataUtilsHelp.pressureJson = it }
    }

    fun updatePressure(pressure: PressureBean) {
        val list = getListFromJson<PressureBean>(DataUtilsHelp.pressureJson)
        list.find { it.id == pressure.id }?.let {
            list[list.indexOf(it)] = pressure
            saveListToJson(list) { DataUtilsHelp.pressureJson = it }
        }
    }

    fun getPressureListData(): MutableList<PressureBean> {
        return getListFromJson<PressureBean>(DataUtilsHelp.pressureJson).sortedByDescending { it.id }.toMutableList()
    }
}