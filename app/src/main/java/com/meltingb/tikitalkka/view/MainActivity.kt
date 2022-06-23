package com.meltingb.tikitalkka.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.google.android.gms.ads.*
import com.meltingb.base.ui.helper.OnKeyBackPressedListener
import com.meltingb.tikitalkka.BuildConfig
import com.meltingb.tikitalkka.R
import com.meltingb.tikitalkka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mOnKeyBackPressedListener: OnKeyBackPressedListener? = null
    private val timeDelay = 2000
    private var pressedTime: Long = 0

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

    fun setBackKeyPressedListener(listener: OnKeyBackPressedListener?) {
        mOnKeyBackPressedListener = listener
    }

    private fun exitApp() {
        if (pressedTime + timeDelay > System.currentTimeMillis()) {
            this.finish()
        } else {
            Toast.makeText(this, getString(R.string.main_back_pressed), Toast.LENGTH_SHORT)
                .show()
        }
        pressedTime = System.currentTimeMillis()
    }

    override fun onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener?.onBackKey()
        } else {
            exitApp()
        }
    }
}