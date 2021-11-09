package com.example.pickyph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){
    private var stdList: ArrayList<HistoryModel> = ArrayList()

    fun addItems(items: ArrayList<HistoryModel>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.history_std, parent, false)
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class HistoryViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvIdHistory)
        private var hasil = view.findViewById<TextView>(R.id.tvHasilHistory)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView (std:HistoryModel){
            id.text = std.id_history.toString()
            hasil.text = std.hasil_history.toString()
        }
    }

}