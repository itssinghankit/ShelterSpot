package com.example.shelterspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EXAMPLE : AppCompatActivity() {
    private lateinit var database:DatabaseReference
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        val userId=FirebaseAuth.getInstance().currentUser!!.uid

        database=FirebaseDatabase.getInstance().getReference("imgUrl")
        database.child(userId).get().addOnSuccessListener {
            val url=it.child("url").value.toString()
            Glide.with(this).load(url).into(findViewById(R.id.imageView2))
        }

    }
}