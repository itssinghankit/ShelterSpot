package com.example.shelterspot.Adapters

import android.content.Context
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shelterspot.HotelDetDataClass
import com.example.shelterspot.R

class HomeFragLocAdapter(val arraylist:ArrayList<HotelDetDataClass>,val context:Context,val listener:onHotelClicked): RecyclerView.Adapter<HomeFragLocAdapter.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hotelname=itemView.findViewById<TextView>(R.id.hotelName)
        val state=itemView.findViewById<TextView>(R.id.state)
        val city=itemView.findViewById<TextView>(R.id.city)
        val description=itemView.findViewById<TextView>(R.id.description)
        val price=itemView.findViewById<TextView>(R.id.price)
        val image=itemView.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.home_page_recycleritem,parent,false)
        val viewHolder=Viewholder(view)
        view.setOnClickListener{
            listener.onitemClicked(viewHolder.adapterPosition)
        }
        return viewHolder

    }

    override fun getItemCount(): Int {
    return arraylist.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.hotelname.text=arraylist[position].hotelname
        holder.city.text=arraylist[position].city
        holder.state.text=arraylist[position].state
        holder.description.text=arraylist[position].description
        holder.price.text=arraylist[position].price
        Glide.with(context).load(arraylist[position].url).placeholder(R.drawable.hotelplaceholder).into(holder.image)

    }

}

interface onHotelClicked{
    fun onitemClicked(position:Int){

    }
}