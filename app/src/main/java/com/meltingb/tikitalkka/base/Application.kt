package com.meltingb.tikitalkka.base

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.google.firebase.FirebaseApp
import com.meltingb.base.utils.AppPreference
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class Application : android.app.Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: Application
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)   // Firebase initialize
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO) // 다크모드 비활성화
        Timber.plant(Timber.DebugTree()) // Timber Log
        AppPreference.init(applicationContext) // AppPreference initialize

        // Koin initialize
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@Application)
            modules(baseModules)
        }
    }
}