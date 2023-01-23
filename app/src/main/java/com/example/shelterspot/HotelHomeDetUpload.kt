package com.example.shelterspot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shelterspot.databinding.ActivityHotelHomeDetUploadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HotelHomeDetUpload : AppCompatActivity() {

    private lateinit var binding:ActivityHotelHomeDetUploadBinding
    private lateinit var storageref:StorageReference
    private lateinit var database:DatabaseReference
    private lateinit var auth:FirebaseAuth
    private var imageUri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotelHomeDetUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email=intent.getStringExtra("email").toString()
        val password=intent.getStringExtra("password").toString()
        var username=""
        var i=0
        while(email[i]!='@'){
            username=username+email[i]
            i++
        }

        binding.selectImage.setOnClickListener{

            resultLauncher.launch("image/*")
        }

        storageref=FirebaseStorage.getInstance().getReference("images").child(username)
        database=FirebaseDatabase.getInstance().getReference("hotels")
        auth=FirebaseAuth.getInstance()

        binding.signup.setOnClickListener{
            binding.signup.visibility=View.INVISIBLE
            binding.progressBar.visibility=View.VISIBLE
                                        //for saving hotel details
            val datobj=HotelDetDataClass(binding.ownerName.text.toString(),binding.hotelName.text.toString(),binding.state.text.toString(),binding.city.text.toString(),binding.area.text.toString(),binding.pincode.text.toString(),binding.mobile.text.toString(),email, binding.price.text.toString(),binding.rooms.text.toString(),binding.description.text.toString())
            database.child(username).setValue(datobj).addOnCompleteListener {
                if(it.isSuccessful){

                    uploadDetails()
                                    //then finally after uploading details,finally signup of hotels
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this, HotelSignin::class.java)
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(this, "Failed, Try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "failed,try again", Toast.LENGTH_SHORT).show()
                    binding.signup.visibility=View.VISIBLE
                    binding.progressBar.visibility=View.INVISIBLE
                }
            }
        }


    }

    private fun uploadDetails() {
        imageUri?.let { imageUri ->                      //as image uri can be null so we used let wrapping
            storageref.child("image").putFile (imageUri).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    binding.signup.visibility=View.VISIBLE
                    binding.progressBar.visibility=View.INVISIBLE
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