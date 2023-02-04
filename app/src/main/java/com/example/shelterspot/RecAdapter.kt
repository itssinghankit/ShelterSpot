package com.example.shelterspot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecAdapter(val dataList:ArrayList<HotelDetDataClass>) : RecyclerView.Adapter<RecAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : ViewHolder(itemView) {
       val hotelName=itemView.findViewById<TextView>(R.id.hotelName)
        val city=itemView.findViewById<TextView>(R.id.city)
        val state = itemView.findViewById<TextView>(R.id.state)
        val price=itemView.findViewById<TextView>(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycleritem,parent,false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.hotelName.text=dataList[position].hotelname
        holder.state.text=dataList[position].state
        holder.city.text=dataList[position].city
        holder.price.text=dataList[position].price
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}