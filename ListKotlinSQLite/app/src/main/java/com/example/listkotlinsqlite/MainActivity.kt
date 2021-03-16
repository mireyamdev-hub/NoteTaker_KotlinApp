package com.example.listkotlinsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listkotlinsqlite.adapters.ListAdapter
import com.example.listkotlinsqlite.data.DBHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var dbHandler: DBHandler
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        dbHandler = DBHandler(this)
        viewItems()
        fab.setOnClickListener{
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
    }

    private fun viewItems(){
        val itemsList = dbHandler.getItems(this)
        val adapter = ListAdapter(this,itemsList)
        val recyclerview = findViewById<RecyclerView>(R.id.rv)
        //Reverse recyclerview: Last items added are shown first
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        recyclerview.layoutManager = layoutManager
        layoutManager?.stackFromEnd = true
        recyclerview.adapter = adapter
    }

    override fun onResume() {
        viewItems()
        super.onResume()
    }
}