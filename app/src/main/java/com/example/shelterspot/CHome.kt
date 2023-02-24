package com.example.shelterspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.shelterspot.databinding.ActivityChomeBinding

class CHome : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding:ActivityChomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.homefragments) as NavHostFragment
        navController=navHostFragment.navController
        val bottomNavigationView=binding.bottomNavView
        setupWithNavController(bottomNavigationView,navController)



    }
}