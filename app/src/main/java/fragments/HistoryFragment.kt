package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shelterspot.Adapters.HistoryAdapter
import com.example.shelterspot.DataClasses.HistoryItemDataclass
import com.example.shelterspot.databinding.FragmentHistoryBinding
import com.google.firebase.database.*


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val arrayList = ArrayList<HistoryItemDataclass>()

                         //recieving customer history details

        database = FirebaseDatabase.getInstance().getReference("custHist")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (datasnapshot in snapshot.children) {
                        val singledata = datasnapshot.getValue(HistoryItemDataclass::class.java)
                        arrayList.add(singledata!!)
                    }
                    binding.recyclerView.adapter = HistoryAdapter(arrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return binding.root
    }
}