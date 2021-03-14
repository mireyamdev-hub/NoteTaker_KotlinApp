package com.example.listkotlinsqlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listkotlinsqlite.R
import com.example.listkotlinsqlite.models.Item
import kotlinx.android.synthetic.main.items.view.*

class ListAdapter(private val context: Context, private val items: Array<Item>) : RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    
    val myContext = context
    
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var txtDescription = itemView.itemDescription
        val btnUpdate = itemView.btnupdate
        val btnDelete = itemView.btndelete
    }
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val item : Item = items[position]
        holder.txtDescription.text = item.itemDescription

    }
}