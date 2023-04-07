package com.example.shelterspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityCmapViewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CMapView : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCmapViewBinding
    private var latitude:Double=0.0
    private var longitude:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCmapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

         latitude=intent.getDoubleExtra("latitude",0.0)
         longitude=intent.getDoubleExtra("longitude",0.0)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val HotelLoc = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(HotelLoc).title("Hotel Location"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(HotelLoc,16f))
    }
}