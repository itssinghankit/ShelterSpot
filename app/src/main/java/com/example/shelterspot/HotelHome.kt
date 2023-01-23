package com.example.shelterspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shelterspot.databinding.ActivityHotelHomeBinding
import com.example.shelterspot.databinding.ActivityHotelSigninBinding

class HotelHome : AppCompatActivity() {
    private lateinit var binding: ActivityHotelHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotelHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}