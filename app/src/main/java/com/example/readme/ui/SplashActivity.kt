package com.example.readme.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.example.readme.ui.login.LoginActivity
import com.example.readme.ui.start.StartFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

            if (isLoggedIn) {
                // 로그인 상태라면 바로 MainActivity로 이동
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // 비회원 상태라면 StartActivity로 이동
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish() // Splash 화면 종료
        }, 2000)
    }
}