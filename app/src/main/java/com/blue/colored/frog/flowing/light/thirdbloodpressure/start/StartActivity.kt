package com.blue.colored.frog.flowing.light.thirdbloodpressure.start

import android.os.Bundle
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivityMainBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import android.content.Intent
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivityStartBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.main.MainActivity
import java.util.concurrent.TimeUnit

class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>() {

    private lateinit var disposable: Disposable
    private lateinit var finishDisposable: Disposable

    override val layoutId: Int
        get() = R.layout.activity_start

    override val viewModelClass: Class<StartViewModel>
        get() = StartViewModel::class.java

    override fun setupViews() {
        onBackPressedDispatcher.addCallback(this) {
        }
    }

    override fun observeViewModel() {
        startCountdown()
    }
    private fun startCountdown() {
        val totalTime = 2000L
        val intervalTime = 20L
        val count = totalTime / intervalTime
        binding.pbStart.isVisible = true
        disposable = Observable.intervalRange(0, count, 0, intervalTime, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { progress ->
                val progressPercentage = ((progress + 1).toFloat() / count.toFloat() * 100).toInt()
            }

        finishDisposable = Completable.timer(totalTime, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.pbStart.isVisible = false
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}