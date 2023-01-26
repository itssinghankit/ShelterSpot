package com.example.shelterspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.shelterspot.databinding.ActivityEditHotelDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditHotelDetails : AppCompatActivity() {
    private lateinit var binding:ActivityEditHotelDetailsBinding
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditHotelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database=FirebaseDatabase.getInstance().getReference("hotels")
        val email=intent.getStringExtra("email").toString()

        var username=""
        var i=0
        while(email[i]!='@'){
            username+=email[i]
            i++
        }

        fetcheddata(username)

        binding.save.setOnClickListener{

            binding.save.visibility=View.INVISIBLE
            binding.progressBar.visibility=View.VISIBLE

            val datobj=HotelDetDataClass(binding.ownerName.text.toString(),binding.hotelName.text.toString(),binding.state.text.toString(),binding.city.text.toString(),binding.area.text.toString(),binding.pincode.text.toString(),binding.mobile.text.toString(),email, binding.price.text.toString(),binding.rooms.text.toString(),binding.description.text.toString())
            database.child(username).setValue(datobj).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "Details Saved", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,HotelHome::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "failed,try again", Toast.LENGTH_SHORT).show()
                    binding.save.visibility= View.VISIBLE
                    binding.progressBar.visibility= View.INVISIBLE
                }
            }

        }

    }

    private fun fetcheddata(username:String) {
        database.child(username).get().addOnSuccessListener {
            binding.hotelName.setText(it.child("hotelname").value.toString())
            binding.city.setText(it.child("city").value.toString())
            binding.state.setText(it.child("state").value.toString())
            binding.description.setText(it.child("description").value.toString())
            binding.price.setText(it.child("price").value.toString())
            binding.ownerName.setText(it.child("ownername").value.toString())
            binding.mobile.setText(it.child("mobile").value.toString())
            binding.pincode.setText(it.child("pincode").value.toString())
            binding.area.setText(it.child("area").value.toString())
            binding.rooms.setText(it.child("rooms").value.toString())
        }
    }
}