package com.example.shelterspot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shelterspot.databinding.ActivityChotelDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CHotelDetails : AppCompatActivity() {

    private lateinit var binding:ActivityChotelDetailsBinding
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChotelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId=intent.getStringExtra("userId").toString()

        var latitude=0.0
        var longitude=0.0

        database=FirebaseDatabase.getInstance().getReference("hotels")
        database.child(userId).get().addOnSuccessListener {
            binding.hotelName.text=it.child("hotelname").value.toString()
            binding.state.text=it.child("state").value.toString()
            binding.city.text=it.child("city").value.toString()+","
            binding.description.text=it.child("description").value.toString()
            binding.price.text=it.child("price").value.toString()
            binding.email.text=it.child("email").value.toString()
            binding.mobile.text=it.child("mobile").value.toString()
            binding.rooms.text=it.child("rooms").value.toString()
            latitude=it.child("latitude").value.toString().toDouble()
            longitude=it.child("longitude").value.toString().toDouble()
            binding.personperroom.text=it.child("personperroom").value.toString()
            binding.address.text="${it.child("area").value.toString()}, ${it.child("city").value.toString()}, ${it.child("state").value.toString()}, India - ${it.child("pincode").value.toString()}"
            Glide.with(this).load(it.child("url").value.toString()).centerCrop().placeholder(R.drawable.hotelplaceholder).into(binding.image)

        }
        binding.back.setOnClickListener{
            finish()
        }

        //after clicking on the map
        binding.map.setOnClickListener{
            val intent=Intent(this,CMapView::class.java)
            intent.putExtra("latitude",latitude)
            intent.putExtra("longitude",longitude)
            startActivity(intent)
        }
        //after book button is clicked
        binding.book.setOnClickListener{

            val intent=Intent(this,HotelBookingDetails::class.java)
            intent.putExtra("userId",userId)
            startActivity(intent)
        }

    }



}