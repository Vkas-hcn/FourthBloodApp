package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.pressure

import androidx.lifecycle.ViewModel
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseViewModel
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.DataUtilsHelp
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.PressureBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PressureViewModel : BaseViewModel() {

    fun savePressure(pressure: PressureBean) {
        val beanType = object : TypeToken<MutableList<PressureBean>>() {}.type
        var listBean =  runCatching {
            Gson().fromJson<MutableList<PressureBean>>(DataUtilsHelp.pressureJson, beanType)
        }.getOrNull()
        if(listBean == null){
            listBean = ArrayList()
        }
        listBean?.add(pressure)
        DataUtilsHelp.pressureJson = Gson().toJson(listBean)
    }

    fun updatePressure(pressure:PressureBean){
        val beanType = object : TypeToken<MutableList<PressureBean>>() {}.type
        var listBean = runCatching {
            Gson().fromJson<MutableList<PressureBean>>(DataUtilsHelp.pressureJson, beanType)
        }.getOrNull()
            ?: return
        for (i in 0 until listBean.size){
            if(listBean[i].id == pressure.id){
                listBean[i] = pressure
                DataUtilsHelp.pressureJson = Gson().toJson(listBean)
                return
            }
        }

    }
    fun getPressureListData():MutableList<PressureBean>{
        val beanType = object : TypeToken<MutableList<PressureBean>>() {}.type
        val pressureList = runCatching {
            Gson().fromJson<MutableList<PressureBean>>(DataUtilsHelp.pressureJson, beanType)
        }.getOrNull()?: mutableListOf()
        pressureList.sortByDescending { it.id }
        return pressureList

    }

    fun deletePressure(pressure: PressureBean){
        val beanType = object : TypeToken<MutableList<PressureBean>>() {}.type
        val listBean =  runCatching {
            Gson().fromJson<MutableList<PressureBean>>(DataUtilsHelp.pressureJson, beanType)
        }
        if(listBean.isSuccess){
            listBean.getOrNull()?.remove(pressure)
        }
        DataUtilsHelp.pressureJson = Gson().toJson(listBean.getOrNull())
    }
}