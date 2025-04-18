package com.wind.rises.strongly.blood.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wind.rises.strongly.blood.databinding.ActivitysSettingBinding

class SettingFragment :  Fragment() {
    private val binding by lazy { ActivitysSettingBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }
     fun setupViews() {
//        incluSet.tvShare.setOnClickListener {
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "text/plain"
//            intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/love-flower/WallPaperApp${this@MainActivity.packageName}")
//            startActivity(Intent.createChooser(intent, "Share on:"))
//        }
        binding.tvPP.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            //TODO 跳转网页
            intent.data =
                android.net.Uri.parse("https://www.google.com")
            startActivity(intent)
        }
    }

}