package com.example.shelterspot

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityHotelHomeDetUploadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HotelHomeDetUpload : AppCompatActivity() {

    private lateinit var binding: ActivityHotelHomeDetUploadBinding
    private lateinit var storageref: StorageReference
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var uri: Uri? = null
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelHomeDetUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val password = intent.getStringExtra("password").toString()
        email = intent.getStringExtra("email").toString()

        Log.d("meow",email)
        //for selecting map location of hotel
        binding.map.setOnClickListener{
          val intent=Intent(this, HotelMapLocSetActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("password",password)
            startActivity(intent)
        }

        //for selecting image
        binding.selectImage.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        database = FirebaseDatabase.getInstance().getReference("hotels")
        auth = FirebaseAuth.getInstance()

        //after map location is set, setting its details to edittexts
        binding.city.setText(intent.getStringExtra("city").toString())
        binding.area.setText(intent.getStringExtra("area").toString())
        binding.state.setText(intent.getStringExtra("state").toString())
        binding.pincode.setText(intent.getStringExtra("pincode").toString())

        binding.signup.setOnClickListener {

            binding.signup.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE

            //for uoloading details


            //for saving hotel details
//            val datobj=HotelDetDataClass(binding.ownerName.text.toString(),binding.hotelName.text.toString(),binding.state.text.toString(),binding.city.text.toString(),binding.area.text.toString(),binding.pincode.text.toString(),binding.mobile.text.toString(),email, binding.price.text.toString(),binding.rooms.text.toString(),binding.description.text.toString())
//            database.child(userId).setValue(datobj).addOnCompleteListener {
//                if(it.isSuccessful){
//
//                    uploadDetails()

            //then finally after uploading details,finally signup of hotels
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    auth.signInWithEmailAndPassword(email, password)
                    if (task.isSuccessful) {
                        uploadDetails()
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        Toast.makeText(this, "Failed, Try again", Toast.LENGTH_SHORT).show()
                    }
                }
//                }else{
//                    Toast.makeText(this, "failed,try again", Toast.LENGTH_SHORT).show()
//                    binding.signup.visibility=View.VISIBLE
//                    binding.progressBar.visibility=View.INVISIBLE
//                }
//            }
        }


    }

    private fun uploadDetails() {
//        imageUri?.let { imageUri ->                      //as image uri can be null so we used let wrapping
//            storageref.child("image").putFile (imageUri).addOnCompleteListener{
//                if(it.isSuccessful){
//                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//                    binding.signup.visibility=View.VISIBLE
//                    binding.progressBar.visibility=View.INVISIBLE
//                }
//            }
//        }
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        storageref = FirebaseStorage.getInstance().getReference("image").child(userId)

        uri?.let { uri ->

            storageref.child(System.currentTimeMillis().toString()).putFile(uri)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uriUrl ->
                        val datobj = HotelDetDataClass(
                            binding.ownerName.text.toString(),
                            binding.hotelName.text.toString(),
                            binding.state.text.toString(),
                            binding.city.text.toString(),
                            binding.area.text.toString(),
                            binding.pincode.text.toString(),
                            binding.mobile.text.toString(),
                            email,
                            binding.price.text.toString(),
                            binding.rooms.text.toString(),
                            binding.description.text.toString(),
                            uriUrl.toString(),
                            userId,
                            binding.personperroom.text.toString(),
                            intent.getStringExtra("latitude").toString(),
                            intent.getStringExtra("longitude").toString()
                        )
                        database.child(userId).setValue(datobj).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "url uploaded", Toast.LENGTH_SHORT).show()
                                binding.signup.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.INVISIBLE
                                val intent = Intent(this, HotelSignin::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "failed url upload", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }

        }

    }

    //this is an object which searches for result and in this case an image result

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri = it
        binding.image.setImageURI(it)
    }

}