package com.example.shelterspot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shelterspot.databinding.ActivityHotelHomeDetUploadBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HotelHomeDetUpload : AppCompatActivity() {

    private lateinit var binding:ActivityHotelHomeDetUploadBinding
    private lateinit var storageref:StorageReference
    private var imageUri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotelHomeDetUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.selectImage.setOnClickListener{

            resultLauncher.launch("image/*")
        }

        storageref=FirebaseStorage.getInstance().getReference("images")

        binding.uploadImage.setOnClickListener{

            imageUri?.let { imageUri ->                      //as image uri can be null so we used let wrapping
                storageref.child(System.currentTimeMillis().toString()).putFile (imageUri).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

                    //this is an object which searches for result and in this case an image result

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri=it
        binding.image.setImageURI(it)
    }

}