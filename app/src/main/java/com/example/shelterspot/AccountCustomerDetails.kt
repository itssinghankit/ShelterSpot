package com.example.shelterspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityAccountCustomerDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AccountCustomerDetails : AppCompatActivity() {
    private lateinit var binding:ActivityAccountCustomerDetailsBinding
    private lateinit var database:DatabaseReference
    private var userId=FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAccountCustomerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database=FirebaseDatabase.getInstance().getReference("customer")

        database.child(userId).get().addOnSuccessListener {
            binding.name.text=it.child("name").value.toString()
            binding.mobile.text=it.child("mobile").value.toString()
            binding.email.text=it.child("email").value.toString()

        }
    }
}