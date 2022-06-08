package com.meltingb.tikitalkka.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.meltingb.tikitalkka.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            moveMain()
        }, 1000)
    }

    private fun moveMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}