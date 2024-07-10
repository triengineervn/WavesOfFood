package com.example.wavesoffood.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivitySignUpBinding

class SignUpActivity: AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickSignUpBtn()
        setOnClickLoginTextView()
    }

    private fun setOnClickSignUpBtn() {
        binding.signUpBtn.setOnClickListener{
            val intent = Intent(this, AddLocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setOnClickLoginTextView() {
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}