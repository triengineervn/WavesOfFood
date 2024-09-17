package com.example.wavesoffood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityMainBinding
import com.example.wavesoffood.fragments.NotificationBottomFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        var navController = findNavController(R.id.fragmentContainerView)
        bottomNav.setupWithNavController(navController)
        binding.notificationButton.setOnClickListener {
            val bottomSheetDialog = NotificationBottomFragment()
            bottomSheetDialog.show(supportFragmentManager, "test")
        }
    }
}