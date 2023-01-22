package com.example.shelterspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shelterspot.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.next.setOnClickListener{

                            //checking inputs fields are correct or not

            if(binding.email.text.toString().isNotEmpty()&&binding.password.text.toString().isNotEmpty()&& binding.confirmPassword.text.toString().isNotEmpty()){
                if(binding.password.text.toString()==binding.confirmPassword.text.toString()){
                    val intent =Intent(this,SignupDetails::class.java)
                      intent.putExtra("email",binding.email.text.toString())
                      intent.putExtra("password",binding.password.text.toString())
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Both Passwords should be Same", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Fields can't be Empty", Toast.LENGTH_SHORT).show()
            }

        }

    }
}