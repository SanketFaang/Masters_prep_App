package com.jhainusa.testsxperts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleTextAdapter(
    private val items: List<String>,       // List of strings to display
    private val onItemClick: (String) -> Unit // Lambda to handle item clicks
) : RecyclerView.Adapter<SimpleTextAdapter.SimpleTextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itembank, parent, false) // Inflate the item layout
        return SimpleTextViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleTextViewHolder, position: Int) {
        val text = items[position]
        holder.bind(text, onItemClick)
    }

    override fun getItemCount() = items.size

    class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.questionbanktext)

        fun bind(text: String, onItemClick: (String) -> Unit) {
            textView.text = text // Set the text for the TextView
            itemView.setOnClickListener {
                onItemClick(text) // Handle item click
            }
        }
    }
}
