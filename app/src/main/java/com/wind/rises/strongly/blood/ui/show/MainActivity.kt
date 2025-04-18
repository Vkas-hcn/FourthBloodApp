package com.wind.rises.strongly.blood.ui.show

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.wind.rises.strongly.blood.R
import com.wind.rises.strongly.blood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }
     fun setupViews() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        binding.buttonPressureFragment.setOnClickListener {
            navController.navigate(R.id.pressureFragment)
        }

        binding.buttonSugarFragment.setOnClickListener {
            navController.navigate(R.id.sugarFragment)
        }
        binding.butSetting.setOnClickListener {
            navController.navigate(R.id.settingFragment)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.pressureFragment -> {
                    binding.buttonPressureFragment.isSelected = true
                    binding.buttonSugarFragment.isSelected = false
                    binding.butSetting.isSelected = false
                }

                R.id.sugarFragment -> {
                    binding.buttonPressureFragment.isSelected = false
                    binding.buttonSugarFragment.isSelected = true
                    binding.butSetting.isSelected = false
                }

                R.id.settingFragment -> {
                    binding.buttonPressureFragment.isSelected = false
                    binding.buttonSugarFragment.isSelected = false
                    binding.butSetting.isSelected = true

                }
            }
        }
    }
}