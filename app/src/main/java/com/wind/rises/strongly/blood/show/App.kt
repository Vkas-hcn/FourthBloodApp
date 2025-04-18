package com.wind.rises.strongly.blood.show

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var appComponent: Context
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = this
    }
}