package com.example.shelterspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shelterspot.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url="https://picsum.photos/300"

        binding.button.setOnClickListener{
            Glide.with(this).load(url).centerCrop().skipMemoryCache(true).diskCacheStrategy(
                DiskCacheStrategy.NONE).placeholder(R.drawable.hotelimg3).into(binding.imageView)
        }


    }
}