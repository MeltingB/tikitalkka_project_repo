package com.meltingb.tikitalkka.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.meltingb.base.helpers.debug
import com.meltingb.base.utils.AppPreference
import com.meltingb.base.utils.NetworkStatus
import com.meltingb.tikitalkka.R
import com.meltingb.tikitalkka.base.Constants.PREF_KEY_USER_UID
import com.meltingb.tikitalkka.utils.Common.showOkButtonDialog

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Firebase Auth 인스턴스 초기화
        auth = FirebaseAuth.getInstance()
        // Firebase 익명 인증 확인
        signInAnonymously()
    }

    private fun moveMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun signInAnonymously() {
        if (NetworkStatus.checkNetworkState(this)) {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 익명인증 성공
                        val uid = auth.currentUser?.uid
                        AppPreference.set(PREF_KEY_USER_UID, uid ?: "")
                        Handler(Looper.getMainLooper()).postDelayed({
                            moveMain()
                        }, 1000)
                    } else {
                        // 익명인증 실패
                        showAuthErrorDialog()
                    }
                }
        } else {
            showAuthErrorDialog()
        }
    }

    private fun showAuthErrorDialog() {
        this.showOkButtonDialog(
            getString(R.string.auth_error),
            getString(R.string.restart)
        ) {
            recreate()
        }
    }

}