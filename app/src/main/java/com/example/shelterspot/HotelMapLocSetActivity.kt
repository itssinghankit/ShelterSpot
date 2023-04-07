package com.example.shelterspot

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.shelterspot.databinding.ActivityHotelMapLocSetBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class HotelMapLocSetActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient:FusedLocationProviderClient
    private lateinit var binding: ActivityHotelMapLocSetBinding
    private lateinit var geocoder: Geocoder
    private var city =""
    private var state=""
    private var pincode=""
    private var area=""
    private var latitude=""
    private var longitude=""

    companion object{
       private const val LOCATION_REQUEST_CODE=1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHotelMapLocSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this)
        geocoder= Geocoder(this, Locale.getDefault())

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val email=intent.getStringExtra("email").toString()
        val password=intent.getStringExtra("password").toString()


        //after the done button is clicked
        binding.donebtn.setOnClickListener{
            if(latitude==""){
                Toast.makeText(this, "Please select a valid location", Toast.LENGTH_SHORT).show()
            }else{
                val intent= Intent(this,HotelHomeDetUpload::class.java)
                intent.putExtra("city",city)
                intent.putExtra("state",state)
                intent.putExtra("area",area)
                intent.putExtra("pincode",pincode)
                intent.putExtra("latitude",latitude)
                intent.putExtra("longitude",longitude)
                intent.putExtra("email",email)
                intent.putExtra("password",password)
                Log.d("meow",latitude + area)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        val india = LatLng(20.5937, 78.9629)
//        mMap.addMarker(MarkerOptions().position(india).title("India"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india,4f))

        setUpMapToCurrentLocation()

        mMap.setOnMapClickListener {
            addMarker(it)

            try {
                val address=geocoder.getFromLocation(it.latitude,it.longitude,1)
                city=address!![0].locality
                state=address[0].adminArea
                pincode=address[0].postalCode
                area=address[0].featureName + ", " +address[0].subLocality
                latitude=it.latitude.toString()
                longitude=it.longitude.toString()

               Log.d("meow",address!![0].getAddressLine(0))
            }catch (e:IOException){

            }
        }
        //adminArea- state
        //locality - city
        //featureName- c-14
        //premises - c-14
        //countryCode - IN
        //countryName - India
        //postalCode - pincode
        //subLocality - vijaynagar
        //getAddressLine(0) - complete address

    }

    private fun setUpMapToCurrentLocation() {
        if (
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled=true
        fusedLocationClient.lastLocation.addOnSuccessListener (this){ location->
            lastLocation=location
            if(location!=null){

                val currLatLng=LatLng(location.latitude,location.longitude)
                addMarker(currLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLatLng,12f))
            }

            //now setting hotel live location




        }
    }

    private fun addMarker(currLatLng:LatLng) {
        mMap.clear()
        val markerOptions=MarkerOptions().position(currLatLng).title("${currLatLng}")
        mMap.addMarker(markerOptions)


    }

    override fun onMapClick(p0: LatLng) {

    }
}