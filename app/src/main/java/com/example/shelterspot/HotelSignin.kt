package com.example.shelterspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.shelterspot.databinding.ActivityHotelSigninBinding
import com.example.shelterspot.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class HotelSignin : AppCompatActivity() {
    private lateinit var binding:ActivityHotelSigninBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotelSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth=FirebaseAuth.getInstance()
        binding.signin.setOnClickListener{
                binding.signin.visibility= View.INVISIBLE
                binding.progressBar.visibility=View.VISIBLE
            val email=binding.email.text.toString()
            val password= binding.password.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this,HotelHome::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, "Signin Failed", Toast.LENGTH_SHORT).show()
                        binding.signin.visibility=View.VISIBLE
                        binding.progressBar.visibility=View.INVISIBLE
                    }
                }

        }

    }
}