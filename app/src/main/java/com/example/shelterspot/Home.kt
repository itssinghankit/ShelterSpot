package com.example.shelterspot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shelterspot.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Home : AppCompatActivity() {

    private lateinit var storage:StorageReference
    private lateinit var database:DatabaseReference
    private lateinit var auth:FirebaseAuth
    private var uri:Uri?=null

    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val url="https://picsum.photos/300"
//
//        binding.button.setOnClickListener{
//            Glide.with(this).load(url).centerCrop().skipMemoryCache(true).diskCacheStrategy(
//                DiskCacheStrategy.NONE).placeholder(R.drawable.hotelimg3).into(binding.imageView)
//        }

        val userId=FirebaseAuth.getInstance().currentUser!!.uid

        binding.imageView.setOnClickListener{
            resultLauncher.launch("image/*")

        }
        storage=FirebaseStorage.getInstance().getReference("img").child(userId)

        binding.button.setOnClickListener{
            uri?.let { uri ->
                storage.child(System.currentTimeMillis().toString()).putFile(uri)
                    .addOnSuccessListener { task->
                        task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri->
                            val imageMap= mapOf("url" to uri.toString())
                            database=FirebaseDatabase.getInstance().getReference("imgUrl")
                            database.child(userId).setValue(imageMap).addOnCompleteListener {
                                if(it.isSuccessful){
                                    Toast.makeText(this, "url uploaded", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(this, "failed url upload", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }

            }

        }

            binding.button2.setOnClickListener{
                val intent=Intent(this,EXAMPLE::class.java)
                startActivity(intent)
            }

    }
    private val resultLauncher= registerForActivityResult(ActivityResultContracts.GetContent()){
        uri=it
        binding.imageView.setImageURI(it)

    }

}