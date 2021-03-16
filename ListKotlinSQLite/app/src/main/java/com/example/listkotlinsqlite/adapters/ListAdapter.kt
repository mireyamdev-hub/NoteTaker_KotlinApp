package com.example.listkotlinsqlite.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.listkotlinsqlite.MainActivity
import com.example.listkotlinsqlite.R
import com.example.listkotlinsqlite.models.Item
import kotlinx.android.synthetic.main.items.view.*
import kotlinx.android.synthetic.main.items_update.view.*

class ListAdapter(private val context: Context, private val items: ArrayList<Item>) : RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    
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

        //Delete note
        holder.btnDelete.setOnClickListener {
            val itemDescription = item.itemDescription

            var alertDialog = AlertDialog.Builder(myContext)
                .setTitle("Caution !")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{dialog, which ->
                    if (MainActivity.dbHandler.deleteItem(item.itemId))
                    {
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,items.size)
                        Toast.makeText(myContext,"Note has been deleted", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(myContext,"Can't delete this note", Toast.LENGTH_LONG).show()
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show()

                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(myContext.getResources().getColor(R.color.colorPrimaryDark))
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(myContext.getResources().getColor(R.color.colorAccent))
        }

        //Update note
        holder.btnUpdate.setOnClickListener {
            val inflater = LayoutInflater.from(myContext)
            val view = inflater.inflate(R.layout.items_update,null)
            val txtDescription : TextView = view.findViewById(R.id.txtupdate)
            val btnClear : Button = view.findViewById(R.id.btncleartxt)
            
            txtDescription.text = item.itemDescription
            
            val builder = AlertDialog.Builder(myContext)
                .setView(view)
                .setIcon(R.drawable.update)
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                    val isUpdate = MainActivity.dbHandler.updateItem(
                        item.itemId.toString(),
                        view.txtupdate.text.toString()
                    )
                    if(isUpdate == true){
                        items[position].itemDescription = view.txtupdate.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(myContext, "Note has been Updated", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(myContext, "Can't update this note", Toast.LENGTH_LONG).show()
                    }
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })

                val alert = builder.create()
                alert.show()

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(myContext.getResources().getColor(R.color.colorPrimaryDark))
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(myContext.getResources().getColor(R.color.colorAccent))

                //Clear field btn inside AlertDialog
                btnClear.setOnClickListener {
                txtDescription.text = ""
            }
        }
    }
}