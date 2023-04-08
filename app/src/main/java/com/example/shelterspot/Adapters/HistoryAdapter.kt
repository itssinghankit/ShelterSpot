package com.example.shelterspot.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shelterspot.DataClasses.HistoryItemDataclass
import com.example.shelterspot.R

class HistoryAdapter(val arraylist:ArrayList<HistoryItemDataclass>): RecyclerView.Adapter<HistoryAdapter.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hotelname = itemView.findViewById<TextView>(R.id.hotelName)
        val datetime = itemView.findViewById<TextView>(R.id.datetime)
        val totalprice = itemView.findViewById<TextView>(R.id.totalprice)
        val circletxt =itemView.findViewById<TextView>(R.id.circletxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Viewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.history_recycler_item,parent,false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
       return arraylist.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.hotelname.text=arraylist[position].hotelname
        holder.datetime.text=arraylist[position].datetime
        holder.totalprice.text="- " + arraylist[position].totalprice
        holder.circletxt.text=arraylist[position].hotelname!![0].toString()
    }
}