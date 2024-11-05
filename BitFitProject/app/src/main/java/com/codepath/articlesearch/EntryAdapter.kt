package com.codepath.articlesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(private var entries: List<EntryEntity>) :
    RecyclerView.Adapter<EntryAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entryTitle: TextView = itemView.findViewById(R.id.entry_title)
        val entryInfo: TextView = itemView.findViewById(R.id.entry_text)
        val date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.entry_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val entry = entries[position]
        holder.entryTitle.text = entry.entryTitle
        holder.entryInfo.text = entry.entryInfo
        holder.date.text = entry.date
    }
    override fun getItemCount(): Int {
        return entries.size
    }

    fun setSessions(entries: List<EntryEntity>) {
        this.entries = entries
        notifyDataSetChanged()
    }
}