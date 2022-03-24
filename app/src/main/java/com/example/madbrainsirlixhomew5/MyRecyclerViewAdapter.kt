package com.example.madbrainsirlixhomew5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(): RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder>() {

    var list = mutableListOf<Contact>()

    class MyRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView

        init {
            name = view.findViewById(R.id.textViewContactName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MyRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        holder.name.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}