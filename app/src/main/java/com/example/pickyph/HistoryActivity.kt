package com.example.pickyph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    private lateinit var btnDeleteAll: Button
    private lateinit var ivHistory: ImageView
    private lateinit var tvIdHistory: TextView
    private lateinit var tvHasikHistory: TextView

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: HistoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        initView()
        initRecyclerViewHistory()
        sqLiteHelper = SQLiteHelper(this)


        getAllHistory()
    }

    private fun initRecyclerViewHistory(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        btnDeleteAll = findViewById(R.id.btnDeleteAll)
        recyclerView = findViewById(R.id.recyclerViewHistory)
    }

    private fun getAllHistory() {
        val stdList = sqLiteHelper.getAllHistory()
        //Log.e("pppp", "$(stdList.size)")

        adapter?.addItems(stdList)

    }
}