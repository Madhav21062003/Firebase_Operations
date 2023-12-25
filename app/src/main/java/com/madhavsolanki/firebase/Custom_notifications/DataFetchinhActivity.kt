package com.madhavsolanki.firebase.Custom_notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madhavsolanki.firebase.databinding.ActivityDataFetchinhBinding

class DataFetchinhActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDataFetchinhBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataFetchinhBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNotificationTitle.setText(intent.getStringExtra("DATA_TITLE"))
        binding.tvNotificationDesc.setText(intent.getStringExtra("DATA_DESC"))


    }
}