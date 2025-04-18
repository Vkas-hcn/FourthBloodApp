package com.wind.rises.strongly.blood.start

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.content.Intent
import android.view.animation.LinearInterpolator
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.wind.rises.strongly.blood.databinding.ActivityStartBinding
import com.wind.rises.strongly.blood.ui.show.MainActivity

class StartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startCountdown()
        onBackPressedDispatcher.addCallback {
        }
    }
    private fun startCountdown() {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 2000
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            binding.proGuide.progress = progress
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(this@StartActivity, MainActivity::class.java))
                finish()
            }
        })
        animator.start()
    }
}