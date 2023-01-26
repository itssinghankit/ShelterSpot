package com.example.shelterspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shelterspot.databinding.ActivityHotelHomeBinding
import com.example.shelterspot.databinding.ActivityHotelSigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HotelHome : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityHotelHomeBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotelHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    auth=FirebaseAuth.getInstance()
    database=FirebaseDatabase.getInstance().getReference("hotels")

    val email=intent.getStringExtra("email").toString()

        var username=""
        var i=0
        while(email[i]!='@'){
            username+=email[i]
            i++
        }

    fetchdata(username)

    binding.back.setOnClickListener{
        val intent=Intent(this,HotelSignin::class.java)
        startActivity(intent)
        finish()
    }

    binding.edit.setOnClickListener{
        val intent=Intent(this,EditHotelDetails::class.java)
        intent.putExtra("email",email)
        startActivity(intent)
    }

    binding.signout.setOnClickListener {
        auth.signOut()
        val intent = Intent(this, HotelSignin::class.java)
        startActivity(intent)
        finish()
    }

    }

    private fun fetchdata(username: String) {

        database.child(username).get().addOnSuccessListener {
            binding.hotelName.setText(it.child("hotelname").value.toString())
            binding.city.setText(it.child("city").value.toString()+",")
            binding.state.setText(it.child("state").value.toString())
            binding.description.setText(it.child("description").value.toString())
            binding.price.setText(it.child("price").value.toString())
            binding.ownerName.setText(it.child("ownername").value.toString())
            binding.mobile.setText(it.child("mobile").value.toString())
            binding.pincode.setText(it.child("pincode").value.toString())
            binding.area.setText(it.child("area").value.toString())
            binding.room.setText(it.child("rooms").value.toString())
        }

    }
}