package com.example.shelterspot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shelterspot.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Home : AppCompatActivity(),onHotelClicked {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var database:DatabaseReference
    private lateinit var dataarrayList:ArrayList<HotelDetDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database=FirebaseDatabase.getInstance().getReference("hotels")

        binding.recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
         dataarrayList=ArrayList<HotelDetDataClass>()

        binding.searchbtn.setOnClickListener{
            searchCity()
        }

        database.addValueEventListener(object:ValueEventListener{       //here ek new class ka object hai jise home class se koi matlab nhi so agar ham this@Home use nhi krenge to ye isi class ko consider krega aur hame ,onHotelClicked bhi pass krna hoga, sath hi onitemclicked ko is value event listener ke object ke andar override krna hoga dono jgah upar aur niche is liye this@Home dena jruri hai
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        val dataoflist=datasnapshot.getValue(HotelDetDataClass::class.java)
                        dataarrayList.add(dataoflist!!)
                    }
                    binding.recycler.adapter=RecAdapter(dataarrayList,this@Home)    //this adapter should be present here as if it is declared down maybe if there is any issue with internet, dataarraylist may be empty but here it will only called up if data snapshot exists.
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun searchCity() {
        dataarrayList.clear()               //to remove existing all data from array list
        database.addValueEventListener(object:ValueEventListener, onHotelClicked {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        val dataoflist=datasnapshot.getValue(HotelDetDataClass::class.java)
                        if(dataoflist!!.city!!.lowercase()==binding.searchCity.text.toString().lowercase()){        //converting to lower case so that any kind of letters can match
                            dataarrayList.add(dataoflist)
                        }
                        else if(binding.searchCity.text.toString()==""){             //if there is no city specified, then show all hotels
                            dataarrayList.add(dataoflist)
                        }

                    }
                    binding.recycler.adapter=RecAdapter(dataarrayList,this@Home)    //this adapter should be present here as if it is declared down maybe if there is any issue with internet, dataarraylist may be empty but here it will only called up if data snapshot exists.
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onItemClicked(pos: Int) {
        Toast.makeText(this, "item clicked is $pos", Toast.LENGTH_SHORT).show()

//        val intent=Intent(this,CHotelDetails::class.java)
//        val map=HashMap<String,String>()
//        map.put("ownername",dataarrayList[pos].ownername.toString())
//        map.put("hotelname",dataarrayList[pos].hotelname.toString())
//        map.put("state",dataarrayList[pos].state.toString())
//        map.put("city",dataarrayList[pos].city.toString())
//        map.put("area",dataarrayList[pos].area.toString())
//        map.put("pincode",dataarrayList[pos].pincode.toString())
//        map.put("mobile",dataarrayList[pos].mobile.toString())
//        map.put("email",dataarrayList[pos].email.toString())
//        map.put("price",dataarrayList[pos].price.toString())
//        map.put("rooms",dataarrayList[pos].rooms.toString())
//        map.put("description",dataarrayList[pos].description.toString())
//        map.put("url",dataarrayList[pos].url.toString())
//        val obj:HotelDetDataClass=dataarrayList[0]
        val intent=Intent(this,CHotelDetails::class.java)
        intent.putExtra("userId",dataarrayList[pos].userId.toString())
        startActivity(intent)
    }
}