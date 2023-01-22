package com.example.shelterspot

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shelterspot.databinding.ActivityMainBinding
import com.example.shelterspot.databinding.ActivityMainBinding.inflate
import com.example.shelterspot.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class Signin : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivitySigninBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth=FirebaseAuth.getInstance()
        binding.signin.setOnClickListener{

            val email=binding.email.text.toString()
            val password= binding.password.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this,Home::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, "Signin Failed", Toast.LENGTH_SHORT).show()
                    }
                }

        }



    }
}