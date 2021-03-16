package com.example.listkotlinsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.listkotlinsqlite.models.Item
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        supportActionBar?.hide()

        //Save new note btn
        btnsavenote.setOnClickListener {
            if (txtnote.text.isEmpty()) {
                Toast.makeText(this, "Note is empty", Toast.LENGTH_LONG).show()
                txtnote.requestFocus()
            } else {
                val item = Item()
                item.itemDescription = txtnote.text.toString()

                MainActivity.dbHandler.addItem(this, item)
                clearFields()
                txtnote.requestFocus()
                finish()
            }
        }
        //Clear and cancel new note btn
        btncancelnote.setOnClickListener {
            clearFields()
            finish()
        }

    }

    //Empty txtfield
    fun clearFields() {
        txtnote.text.clear()
    }

}