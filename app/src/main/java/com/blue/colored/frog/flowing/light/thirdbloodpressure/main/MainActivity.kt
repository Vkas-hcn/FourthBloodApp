package com.blue.colored.frog.flowing.light.thirdbloodpressure.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivityMainBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseActivity
import com.blue.colored.frog.flowing.light.thirdbloodpressure.main.pressure.PressureFragment
import com.blue.colored.frog.flowing.light.thirdbloodpressure.setting.SettingActivity
import com.blue.colored.frog.flowing.light.thirdbloodpressure.start.StartViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
    private lateinit var navController: NavController
    override fun setupViews() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        binding.imgSetting.setOnClickListener {
            navigateTo(SettingActivity::class.java)
        }
       binding.buttonPressureFragment.setOnClickListener {
           navController.navigate(R.id.pressureFragment)
           binding.textView.text="Blood Pressure"

       }

        binding.buttonSugarFragment.setOnClickListener {
            navController.navigate(R.id.sugarFragment)
            binding.textView.text="Blood Sugar"
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.pressureFragment -> {
                    binding.buttonPressureFragment.isSelected = true
                    binding.buttonSugarFragment.isSelected = false

                }
                R.id.sugarFragment -> {
                    binding.buttonPressureFragment.isSelected = false
                    binding.buttonSugarFragment.isSelected = true
                }
            }
        }
    }

    override fun observeViewModel() {

    }


}