package com.example.madbrainsirlixhomew5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(private val list: MutableList<Contact>): RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder>() {
    class MyRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView
        val number: TextView

        init {
            name = view.findViewById(R.id.textViewContactName)
            number = view.findViewById(R.id.textViewContactNumber)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MyRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.number.text = list[position].number

    }

    override fun getItemCount(): Int {
        return list.size
    }
}