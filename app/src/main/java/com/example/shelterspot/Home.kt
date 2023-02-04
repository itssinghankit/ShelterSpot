package com.example.shelterspot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shelterspot.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Home : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database=FirebaseDatabase.getInstance().getReference("hotels")

        binding.recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val dataarrayList=ArrayList<HotelDetDataClass>()

        database.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        val dataoflist=datasnapshot.getValue(HotelDetDataClass::class.java)
                        dataarrayList.add(dataoflist!!)
                    }
                    binding.recycler.adapter=RecAdapter(dataarrayList)    //this adapter should be present here as if it is declared down maybe if there is any issue with internet, dataarraylist may be empty but here it will only called up if data snapshot exists.
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}