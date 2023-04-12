package fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shelterspot.AccountCustomerDetails
import com.example.shelterspot.MainActivity
import com.example.shelterspot.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AccountFragment : Fragment() {
    private lateinit var binding:FragmentAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAccountBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance().getReference("customer")

        val userId=auth.currentUser!!.uid

        database.child(userId).get().addOnSuccessListener {
            binding.personname.text=it.child("name").value.toString()
        }

        //after show details is clicked
        binding.showdetails.setOnClickListener{
            val intent=Intent(context,AccountCustomerDetails::class.java)
            startActivity(intent)
        }

        //after signout button is clicked
        binding.signout.setOnClickListener{
            auth.signOut()
            val intent=Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}