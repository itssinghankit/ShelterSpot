package com.example.shelterspot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signin.setOnClickListener{
            val intent= Intent(this,Signin::class.java)
            startActivity(intent)
        }

        binding.signup.setOnClickListener{
            val intent=Intent(this,Signup::class.java)
            startActivity(intent)
        }

        binding.hotel.setOnClickListener {
            val intent = Intent(this, HotelSigninSignup::class.java)
            startActivity(intent)
        }

    }

    public override fun onStart() {
        auth=FirebaseAuth.getInstance()
        super.onStart()
        val currentUser=auth.currentUser
        if(currentUser!=null){
            val intent=Intent(this,CHome::class.java)
            startActivity(intent)
            finish()
        }
    }
}