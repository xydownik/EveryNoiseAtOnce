package com.example.everynoiseatonce.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.databinding.ActivitySplashBinding
import com.example.everynoiseatonce.presentation.activity.MainActivity
import com.example.everynoiseatonce.presentation.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animAppear = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        binding.textEvery.startAnimation(animAppear)
        binding.textEvery.postDelayed({
            binding.textNoise.visibility = View.VISIBLE
            binding.textNoise.startAnimation(animAppear)
        }, 500)

        binding.textNoise.postDelayed({
            binding.textAt.visibility = View.VISIBLE
            binding.textAt.startAnimation(animAppear)
        }, 1000)

        binding.textAt.postDelayed({
            binding.textOnce.visibility = View.VISIBLE
            binding.textOnce.startAnimation(animAppear)
        }, 1500)

        binding.textOnce.postDelayed({
            binding.buttonStart.visibility = View.VISIBLE
            binding.buttonStart.startAnimation(animAppear)
        }, 2000)

        binding.buttonStart.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = if (FirebaseAuth.getInstance().currentUser != null) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }, 0)
        }

    }
}
