package com.meltingb.tikitalkka.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.meltingb.base.helpers.debug
import com.meltingb.tikitalkka.R

class FullScreenAdActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_ad)

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            getString(R.string.front_ad_unit_id_test),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    this@FullScreenAdActivity.finish()
                    overridePendingTransition(0, 0)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd!!.show(this@FullScreenAdActivity)
                    fullScreenAdCallback()
                }
            })
    }

    private fun fullScreenAdCallback() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
                this@FullScreenAdActivity.finish()
                overridePendingTransition(0, 0)
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                debug("onAdDismissedFullScreenContent ====")
                mInterstitialAd = null
                this@FullScreenAdActivity.finish()
                overridePendingTransition(0, 0)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                super.onAdFailedToShowFullScreenContent(adError)
                debug("onAdFailedToShowFullScreenContent ====")
                mInterstitialAd = null
                this@FullScreenAdActivity.finish()
                overridePendingTransition(0, 0)
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
            }
        }
    }

    override fun onBackPressed() {
        return
    }
}