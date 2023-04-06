package com.example.shelterspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.databinding.ActivityHotelBookingDetailsBinding
import com.google.android.gms.maps.GoogleMap

class HotelBookingDetails : AppCompatActivity(){

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityHotelBookingDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityHotelBookingDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_hotel_booking_details)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        val fragmentManager=supportFragmentManager
//        val fragmentTransaction=fragmentManager.beginTransaction()
//        fragmentTransaction.add(R.id.map,)
//        fragmentTransaction.commit()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
}