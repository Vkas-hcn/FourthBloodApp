package com.blue.colored.frog.flowing.light.thirdbloodpressure.setting

import android.os.Bundle
import androidx.activity.addCallback
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivityMainBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivitysSettingBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseActivity
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.DataUtilsHelp
import com.blue.colored.frog.flowing.light.thirdbloodpressure.start.StartViewModel

class SettingActivity : BaseActivity<ActivitysSettingBinding, SettingViewModel>() {
    override val layoutId: Int
        get() = R.layout.activitys_setting

    override val viewModelClass: Class<SettingViewModel>
        get() = SettingViewModel::class.java

    override fun setupViews() {
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        binding.atvBack.setOnClickListener {
            finish()
        }
        binding.atvPrivacyPolicy.setOnClickListener {
            viewModel.goToBrowser(this,DataUtilsHelp.basePPUrl)
        }
    }

    override fun observeViewModel() {

    }
}