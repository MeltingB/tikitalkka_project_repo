package com.meltingb.tikitalkka.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.google.android.gms.ads.*
import com.meltingb.tikitalkka.BuildConfig
import com.meltingb.tikitalkka.R
import com.meltingb.tikitalkka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ADMOB 광고 SDK 초기화
        MobileAds.initialize(this)
        initBannerAd()

    }

    private fun initBannerAd() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                binding.adView.visibility = View.GONE
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                binding.adView.visibility = View.VISIBLE
            }
        }
    }
}