package com.example.shelterspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityHotelBookingDetailsBinding

class HotelBookingDetails : AppCompatActivity(){

    private lateinit var binding: ActivityHotelBookingDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBookingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}