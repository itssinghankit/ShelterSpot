package com.example.shelterspot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityHotelBookingDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HotelBookingDetails : AppCompatActivity(){

    private lateinit var binding: ActivityHotelBookingDetailsBinding
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBookingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId=intent.getStringExtra("userId").toString()
        database=FirebaseDatabase.getInstance().getReference("hotels")
        database.child(userId).get().addOnSuccessListener {

        //to set the prices at their respective places
            setPrices(it.child("price").value.toString())
        }

        //back button
        binding.back.setOnClickListener{
            val intent=Intent(this,CHotelDetails::class.java)
            intent.putExtra("userId",userId)
            startActivity(intent)
            finish()
        }

    }

    private fun setPrices(price: String) {
        val gst=(18*price.toDouble())/100
        val total=price.toDouble()+gst
        binding.mrp.text=price.toDouble().toString()
        binding.gst.text=gst.toString()
        binding.total.text=total.toString()
        binding.botttomTotal.text=total.toString()
    }
}