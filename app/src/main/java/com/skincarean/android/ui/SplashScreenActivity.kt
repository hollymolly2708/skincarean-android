package com.skincarean.android.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.skincarean.android.R
import com.skincarean.android.databinding.ActivitySplashScreenBinding
import com.skincarean.android.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading()
    }

    private fun loading() {
        Handler().postDelayed(Runnable {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }
}