package com.example.shelterspot

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.shelterspot.DataClasses.HistoryItemDataclass
import com.example.shelterspot.databinding.ActivityHotelBookingDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HotelBookingDetails : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityHotelBookingDetailsBinding
    private lateinit var database: DatabaseReference
    private lateinit var email: String
    private lateinit var mobile: String
    private lateinit var rooms:String
    private lateinit var userId:String
    private lateinit var hotelname:String

    companion object {
        private var total: Double = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBookingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()
        database = FirebaseDatabase.getInstance().getReference("hotels")
        database.child(userId).get().addOnSuccessListener {

            //to set the prices at their respective places
            total = setPrices(it.child("price").value.toString())
            email = it.child("email").value.toString()
            mobile = it.child("mobile").value.toString()
            rooms=it.child("rooms").value.toString()
            hotelname=it.child("hotelname").value.toString()
        }

        //back button
        binding.back.setOnClickListener {
            val intent = Intent(this, CHotelDetails::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
            finish()
        }

        //payment button
        binding.payment.setOnClickListener {
            startPayment(total)
        }

    }

    private fun setPrices(price: String): Double {
        val gst = ((18 * price.toDouble()) / 100).roundToInt()
        val total = price.toDouble() + gst
        binding.mrp.text = price.toDouble().toString()
        binding.gst.text = gst.toDouble().toString()
        binding.total.text = total.toString()
        binding.botttomTotal.text = total.toString()
        return total
    }

    private fun startPayment(total: Double) {

        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_B4xx6GvE5IdEDB")

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#260059");
            options.put("currency", "INR");
            options.put(
                "amount",
                (total * 100).toInt().toString()
            )//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email", email)
            prefill.put("contact", mobile)
            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show()
        val a:Int

        //updating rooms number which is now one less than original in hotels database
        val roomMap=HashMap<String,String>()
        roomMap["rooms"]=(rooms.toInt()-1).toString()
        database.child(userId).updateChildren(roomMap as Map<String, Any>)

        //saving history

        historyDataUpload()

        val intent=Intent(this,CHome::class.java)
        startActivity(intent)
        finish()

    }


    private fun historyDataUpload() {
        //for current date and time
        val currentdatetime= Date()
        val dateformat= SimpleDateFormat("dd-MM-yyyy HH:mm a")
        val datetime=dateformat.format(currentdatetime)

        //now uploading in the database
        database=FirebaseDatabase.getInstance().getReference("custHist")
        database.child(FirebaseAuth.getInstance().currentUser!!.uid).child(System.currentTimeMillis().toString()).setValue(HistoryItemDataclass(hotelname, total.toString(),datetime))

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment failed lol", Toast.LENGTH_SHORT).show()
    }
}