package com.example.pa_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed({

            // Delay and Start Activity
            val intent = Intent(this, MainActivity::class.java)
            //val intent = Intent(this,ProductDetailsNutritionalValuesFragment::class.java)
            startActivity(intent)
            finish()
        }, 3000) // here we're delaying to startActivity after 3seconds
    }
}