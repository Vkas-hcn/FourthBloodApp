package com.blue.colored.frog.flowing.light.thirdbloodpressure.key

import android.content.Context
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.App

object DataUtilsHelp {
    const val basePPUrl = "https://www.baidu.com/privacy-policy"
    private val sharedPreferences by lazy {
        App.appComponent.getSharedPreferences(
            "TBP_KEY",
            Context.MODE_PRIVATE
        )
    }
    var tbp_id = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("tbp_id", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("tbp_id", "").toString()

    var tbp_id_black = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("tbp_id_black", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("tbp_id_black", "").toString()

    var pressureJson = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("pressureJson", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("pressureJson", "").toString()


    var sugarJson = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("sugarJson", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("sugarJson", "").toString()

    var sugarUnit= ""
        set(value) {
            sharedPreferences.edit().run {
                putString("sugarUnit", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("sugarUnit", "").toString()
}