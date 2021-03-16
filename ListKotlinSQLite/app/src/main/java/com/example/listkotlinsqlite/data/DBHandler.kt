package com.example.listkotlinsqlite.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.listkotlinsqlite.models.Item
import java.lang.Exception

class DBHandler (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "notesList.db"
        private val DATABASE_VERSION = 1
        //database content
        val LIST_TABLE_NAME = "listitems"
        val COLUMN_ITEM_ID = "itemid"
        val COLUMN_ITEM_DESCRIPTION = "itemdescription"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Boolean values are stored as integers 0 (false) and 1 (true)
        val CREATE_LIST_TABLE = ("CREATE TABLE $LIST_TABLE_NAME (" +
                "$COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_ITEM_DESCRIPTION TEXT)"
        )
        db?.execSQL(CREATE_LIST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getItems(myContext : Context) : ArrayList<Item>{
        val query = "Select * From $LIST_TABLE_NAME"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        val items = ArrayList<Item>()

        if(cursor.count == 0){
            Toast.makeText(myContext,"No items found", Toast.LENGTH_LONG).show()
        }else{
            while (cursor.moveToNext()) {
                val item = Item()
                item.itemId = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID))
                item.itemDescription = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION))
                items.add(item)
            }
               //Log.e(ContentValues.TAG, "${cursor.count.toString()} Records Found")
        }
        cursor.close()
        database.close()
        return items
    }

    //Function: add new item
    fun addItem (myContext: Context, item: Item){
        val values = ContentValues()
        values.put(COLUMN_ITEM_DESCRIPTION, item.itemDescription)
        val database = this.writableDatabase
        try{
            database.insert(LIST_TABLE_NAME, null, values)
            Toast.makeText(myContext, "New note added to the list", Toast.LENGTH_LONG).show()
        }catch (e : Exception){
            Toast.makeText(myContext, "Exception ${e.message}", Toast.LENGTH_LONG).show()
        }
        database.close()
    }

    //Function delete item
    fun deleteItem(itemId : Int) : Boolean{
        val query = "Delete From $LIST_TABLE_NAME where $COLUMN_ITEM_ID = $itemId"
        val database = this.writableDatabase
        var result : Boolean = false
        try{
            val cursor = database.execSQL(query)
            result = true
        }catch (e : Exception){
            Log.e(ContentValues.TAG, "Deleting ERROR ${e.message}")
        }
        database.close()
        return result
    }

    //Function update existing item
    fun updateItem(itemId : String, itemDescription : String) : Boolean{   //, itemDoneorNot : String
        val database = this.writableDatabase
        val contentValues = ContentValues()
        var result : Boolean = false
        contentValues.put(COLUMN_ITEM_DESCRIPTION, itemDescription)

        try{
            database.update(LIST_TABLE_NAME, contentValues, "$COLUMN_ITEM_ID = ?", arrayOf(itemId))
            result = true
        }catch (e : Exception){
            Log.e(ContentValues.TAG, "Updating ERROR ${e.message}")
            result = false
        }
        return result
    }
}