package com.example.shelterspot

import android.content.Context
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class RecAdapter(val dataList:ArrayList<HotelDetDataClass>,val listener: onHotelClicked) : RecyclerView.Adapter<RecAdapter.myViewHolder>() {
    private lateinit var context:Context
    class myViewHolder(itemView: View) : ViewHolder(itemView) {
       val hotelName=itemView.findViewById<TextView>(R.id.hotelName)
        val city=itemView.findViewById<TextView>(R.id.city)
        val state = itemView.findViewById<TextView>(R.id.state)
        val price=itemView.findViewById<TextView>(R.id.price)
        val image=itemView.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycleritem,parent,false)
        context=parent.context
        val myVH=myViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(myVH.adapterPosition)
        }
        return myVH
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.hotelName.text=dataList[position].hotelname
        holder.state.text=dataList[position].state
        holder.city.text=dataList[position].city+","
        holder.price.text=dataList[position].price
        Glide.with(context).load(dataList[position].url).centerCrop().placeholder(R.drawable.hotelplaceholder).into(holder.image)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}
interface onHotelClicked{           //iterface by default abstract hota hai
    fun onItemClicked(pos:Int){

    }
}