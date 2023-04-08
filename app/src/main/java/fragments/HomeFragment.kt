package fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shelterspot.Adapters.HomeFragLocAdapter
import com.example.shelterspot.Adapters.onHotelClicked
import com.example.shelterspot.CHotelDetails
import com.example.shelterspot.HotelDetDataClass
import com.example.shelterspot.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment(), onHotelClicked {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var arraylist: ArrayList<HotelDetDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(
            layoutInflater,
            container,
            false
        )  //container false ke bina bhi work kr skta hai

        //for setting user name
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference("customer")
        database.child(userId).get().addOnSuccessListener {
            binding.name.text = it.child("name").value.toString()
        }

        database = FirebaseDatabase.getInstance().getReference("hotels")
        arraylist = ArrayList()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        //for putting data to location recycler view
        fetchLocationRecyclerData()

        return binding.root
    }

    private fun fetchLocationRecyclerData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (datasnapshot in snapshot.children) {
                        val singledata = datasnapshot.getValue(HotelDetDataClass::class.java)
                        arraylist.add(singledata!!)
                    }
                    binding.recyclerView.adapter =
                        HomeFragLocAdapter(arraylist, context!!, this@HomeFragment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    //click handles of location recycler view
    override fun onitemClicked(position: Int) {
        super.onitemClicked(position)

        val intent = Intent(
            context,
            CHotelDetails::class.java
        )      //this ki jagah context ya activity use kar skte hai
        intent.putExtra("userId", arraylist[position].userId)
        startActivity(intent)
    }

}