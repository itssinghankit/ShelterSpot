package com.example.shelterspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shelterspot.databinding.ActivitySignupDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignupDetails : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivitySignupDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        val email=intent.getStringExtra("email").toString()
        val password=intent.getStringExtra("password").toString()

        binding.signup.setOnClickListener{

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        auth.signInWithEmailAndPassword(email, password)
                        val userId=auth.currentUser!!.uid
                        val cstmdetobj=CstmrDetData(binding.name.text.toString(),binding.mobile.text.toString(),email)
                        database=FirebaseDatabase.getInstance().getReference("customer")
                        database.child(userId).setValue(cstmdetobj).addOnCompleteListener {

                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                            val intent= Intent(this,Signin::class.java)
                            startActivity(intent)
                            finish()
                        }

                    } else {
                        Toast.makeText(this, "Failed, Try again", Toast.LENGTH_SHORT).show()

                    }
                }

        }

//        println("$email + $password")
//
//        binding.signup.setOnClickListener{
//            if(binding.name.text.toString().isNotEmpty()&&binding.mobile.text.toString().isNotEmpty()){
//                if(binding.mobile.text.toString().length==10){
//
//                                  //For saving details of customer
//
//                    val cstmdetobj=CstmrDetData(binding.name.text.toString(),binding.mobile.text.toString())
//                    database=FirebaseDatabase.getInstance().getReference("customer")
//
//                                    //For unique username
////                    var username=""
////                    var i=0
////                    while(email[i]!='@'){
////                        username = username + email[i]
////                        i++
////                    }
//
//
//                    .addOnCompleteListener {
//                        if(it.isSuccessful){
//
//                            auth.createUserWithEmailAndPassword(email, password)
//                                .addOnCompleteListener(this) { task ->
//
//                                    val userId=auth.uid
//                                    database.child(userId).setValue(cstmdetobj)
//
//                                    if (task.isSuccessful) {
//                                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
//                                        val intent= Intent(this,Signin::class.java)
//                                        startActivity(intent)
//                                        finish()
//
//                                    } else {
//                                        Toast.makeText(this, "Failed, Try again", Toast.LENGTH_SHORT).show()
//
//                                    }
//                                }
//
//                        }else{
//                            Toast.makeText(this, "Failed, try Again", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//
//                }else{
//                    Toast.makeText(this, "Enter Correct Number", Toast.LENGTH_SHORT).show()
//                }
//            }else{
//                Toast.makeText(this, "Fields can't be Empty", Toast.LENGTH_SHORT).show()
//            }
//        }

    }
}