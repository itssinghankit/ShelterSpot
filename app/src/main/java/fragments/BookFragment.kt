package fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shelterspot.*
import com.example.shelterspot.databinding.FragmentBookBinding
import com.google.firebase.database.*

class BookFragment : Fragment(), onHotelClicked {
    private lateinit var binding:FragmentBookBinding
    private lateinit var database: DatabaseReference
    private lateinit var dataarrayList:ArrayList<HotelDetDataClass>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentBookBinding.inflate(layoutInflater)

        database= FirebaseDatabase.getInstance().getReference("hotels")

        binding.recycler.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        dataarrayList=ArrayList<HotelDetDataClass>()

        binding.searchbtn.setOnClickListener{
            searchCity()
        }


        database.addValueEventListener(object: ValueEventListener {       //here ek new class ka object hai jise home class se koi matlab nhi so agar ham this@Home use nhi krenge to ye isi class ko consider krega aur hame ,onHotelClicked bhi pass krna hoga, sath hi onitemclicked ko is value event listener ke object ke andar override krna hoga dono jgah upar aur niche is liye this@Home dena jruri hai
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        val dataoflist=datasnapshot.getValue(HotelDetDataClass::class.java)
                        dataarrayList.add(dataoflist!!)
                    }
                    binding.recycler.adapter= RecAdapter(dataarrayList,this@BookFragment)    //this adapter should be present here as if it is declared down maybe if there is any issue with internet, dataarraylist may be empty but here it will only called up if data snapshot exists.
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


        return binding.root

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
                    binding.recycler.adapter=RecAdapter(dataarrayList,this@BookFragment)    //this adapter should be present here as if it is declared down maybe if there is any issue with internet, dataarraylist may be empty but here it will only called up if data snapshot exists.
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onItemClicked(pos: Int) {
        Toast.makeText(context, "item clicked is $pos", Toast.LENGTH_SHORT).show()

        val intent= Intent(context, CHotelDetails::class.java)
        intent.putExtra("userId",dataarrayList[pos].userId.toString())
        startActivity(intent)
    }

}