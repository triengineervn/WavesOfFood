package com.example.wavesoffood.activities

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityAddLocationBinding

class AddLocationActivity : AppCompatActivity() {
    private val binding: ActivityAddLocationBinding by lazy {
        ActivityAddLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf("Tokyo","New York","Ho Chi Minh","Bang Kok")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }
}