package com.example.pickyph

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ph.db"
        private const val TBL_HISTORY = "tbl_history"
        private const val ID_HISTORY = "id_history"
        private const val HASIL_HISTORY = "hasil_history"
        private const val R_HISTORY = "r_history"
        private const val G_HISTORY = "g_history"
        private const val B_HISTORY = "b_history"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblHistory = ("CREATE TABLE $TBL_HISTORY($ID_HISTORY INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " $HASIL_HISTORY INTEGER, $R_HISTORY INTEGER, $G_HISTORY INTEGER, $B_HISTORY INTEGER)")
        db?.execSQL(createTblHistory)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_HISTORY")
        onCreate(db)
    }


    fun insertHistory(std: HistoryModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        //contentValues.put(ID_HISTORY, std.id_history)
        contentValues.put(HASIL_HISTORY, std.hasil_history)
        contentValues.put(R_HISTORY, std.r_history)
        contentValues.put(G_HISTORY, std.g_history)
        contentValues.put(B_HISTORY, std.b_history)

        val success = db.insert(TBL_HISTORY, null, contentValues)
        db.close()
        return success
    }


    fun getAllHistory(): ArrayList<HistoryModel>{
        val stdList: ArrayList<HistoryModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_HISTORY"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id_history: Int
        var hasil_history: Int
        var r_history: Int
        var g_history: Int
        var b_history: Int

        if (cursor.moveToFirst()){
            do {
                id_history = cursor.getInt(cursor.getColumnIndexOrThrow("id_history"))
                hasil_history = cursor.getInt(cursor.getColumnIndexOrThrow("hasil_history"))
                r_history = cursor.getInt(cursor.getColumnIndexOrThrow("r_history"))
                g_history = cursor.getInt(cursor.getColumnIndexOrThrow("g_history"))
                b_history = cursor.getInt(cursor.getColumnIndexOrThrow("b_history"))

                val std = HistoryModel(
                    id_history = id_history,
                    hasil_history = hasil_history,
                    r_history = r_history,
                    g_history = g_history,
                    b_history = b_history
                )
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        return stdList
    }




}