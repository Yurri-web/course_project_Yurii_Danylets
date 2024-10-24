package com.example.lb_3_androidstudio_danylets

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView = view.findViewById(R.id.item_text)
        val itemSwitch: SwitchCompat = view.findViewById(R.id.item_switch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.itemText.text = if (currentItem.isChecked) "ON" else "OFF"
        holder.itemText.setTextColor(
            if (currentItem.isChecked) Color.GREEN else Color.RED
        )

        holder.itemSwitch.isChecked = currentItem.isChecked

        holder.itemSwitch.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isChecked = isChecked
            holder.itemText.text = if (isChecked) "ON" else "OFF"
            holder.itemText.setTextColor(if (isChecked) Color.GREEN else Color.RED)
        }
    }

    override fun getItemCount(): Int = items.size
}
