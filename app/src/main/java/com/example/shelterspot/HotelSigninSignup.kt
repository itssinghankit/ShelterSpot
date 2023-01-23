package com.example.shelterspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shelterspot.databinding.ActivityHotelHomeDetUploadBinding
import com.example.shelterspot.databinding.ActivityHotelSigninSignupBinding

class HotelSigninSignup : AppCompatActivity() {

    private lateinit var binding: ActivityHotelSigninSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHotelSigninSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener{
            val intent=Intent(this,HotelSignup::class.java)
            startActivity(intent)
        }

        binding.signin.setOnClickListener{
            val intent=Intent(this,HotelSignin::class.java)
            startActivity(intent)
        }

    }
}