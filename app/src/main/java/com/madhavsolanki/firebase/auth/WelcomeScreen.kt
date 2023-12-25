package com.madhavsolanki.firebase.auth

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.madhavsolanki.firebase.MainActivity
import com.madhavsolanki.firebase.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val user = auth.currentUser

        if (user == null) {
            goToLogin()
        }
        else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                finish()
            },2000)

            val welcomeText = "Welcome"
            val spannableString = SpannableString(welcomeText)
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")),0,5,0)
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#312222")),5, welcomeText.length,0)

            binding.welcomeText.text = spannableString
        }
    }

    private fun  goToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@WelcomeScreen, LoginScreen::class.java))
            finish()
        },2000)

        val welcomeText = "Welcome"
        val spannableString = SpannableString(welcomeText)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")),0,5,0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#312222")),5, welcomeText.length,0)

        binding.welcomeText.text = spannableString
    }
}