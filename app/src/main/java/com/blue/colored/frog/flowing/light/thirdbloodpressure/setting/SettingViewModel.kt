package com.blue.colored.frog.flowing.light.thirdbloodpressure.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseViewModel

class SettingViewModel:BaseViewModel() {

    fun goToBrowser(activity: AppCompatActivity,url:String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        activity.startActivity(intent)
    }
}