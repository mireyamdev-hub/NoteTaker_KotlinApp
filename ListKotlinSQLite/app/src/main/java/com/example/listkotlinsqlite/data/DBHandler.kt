package com.example.listkotlinsqlite.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "notesList.db"
        private val DATABASE_VERSION = 1
        //database content
        val LIST_TABLE_NAME = "listitems"
        val COLUMN_ITEM_ID = "itemid"
        val COLUMN_ITEM_DESCRIPTION = "itemdescription"
        val COLUMN_ITEM_DONE = "doneornot"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Boolean values are stored as integers 0 (false) and 1 (true)
        val CREATE_LIST_TABLE = ("CREATE TABLE $LIST_TABLE_NAME (" +
                "$COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_ITEM_DESCRIPTION TEXT," +
                "$COLUMN_ITEM_DONE INTEGER DEFAULT 0)"
        )
        db?.execSQL(CREATE_LIST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}