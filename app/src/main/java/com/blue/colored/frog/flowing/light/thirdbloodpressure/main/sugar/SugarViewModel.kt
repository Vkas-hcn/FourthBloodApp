package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.sugar

import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseViewModel
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.DataUtilsHelp
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.SugarBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SugarViewModel:BaseViewModel() {

    fun saveSugar(pressure: SugarBean) {
        val beanType = object : TypeToken<MutableList<SugarBean>>() {}.type
        var listBean =  runCatching {
            Gson().fromJson<MutableList<SugarBean>>(DataUtilsHelp.sugarJson, beanType)
        }.getOrNull()
        if(listBean == null){
            listBean = ArrayList()
        }
        listBean?.add(pressure)
        DataUtilsHelp.sugarJson = Gson().toJson(listBean)
    }

    fun updateSugar(pressure: SugarBean){
        val beanType = object : TypeToken<MutableList<SugarBean>>() {}.type
        var listBean = runCatching {
            Gson().fromJson<MutableList<SugarBean>>(DataUtilsHelp.sugarJson, beanType)
        }.getOrNull()
            ?: return
        for (i in 0 until listBean.size){
            if(listBean[i].id == pressure.id){
                listBean[i] = pressure
                DataUtilsHelp.sugarJson = Gson().toJson(listBean)
                return
            }
        }

    }
    fun getSugarListData():MutableList<SugarBean>{
        val beanType = object : TypeToken<MutableList<SugarBean>>() {}.type
        val pressureList = runCatching {
            Gson().fromJson<MutableList<SugarBean>>(DataUtilsHelp.sugarJson, beanType)
        }.getOrNull()?: mutableListOf()
        pressureList.sortByDescending { it.id }
        return pressureList

    }

    fun deleteSugar(pressure: SugarBean){
        val beanType = object : TypeToken<MutableList<SugarBean>>() {}.type
        val listBean =  runCatching {
            Gson().fromJson<MutableList<SugarBean>>(DataUtilsHelp.sugarJson, beanType)
        }
        if(listBean.isSuccess){
            listBean.getOrNull()?.remove(pressure)
        }
        DataUtilsHelp.sugarJson = Gson().toJson(listBean.getOrNull())
    }
}