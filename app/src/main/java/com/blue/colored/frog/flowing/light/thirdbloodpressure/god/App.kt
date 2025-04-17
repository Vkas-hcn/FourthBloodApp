package com.blue.colored.frog.flowing.light.thirdbloodpressure.god

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var appComponent: Context
       var clickId = ""
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = this
    }
}