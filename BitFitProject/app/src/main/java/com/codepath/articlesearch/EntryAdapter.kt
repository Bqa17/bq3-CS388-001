package com.codepath.articlesearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(
    private val context: Context,
    private val entries: MutableList<DisplayEntry>,
    private val lifecycleScope: EntryApplication,
    private val onItemLongClicked: (Int) -> Unit
) :
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

        holder.itemView.setOnLongClickListener {
            onItemLongClicked(entry.id)
            true
        }
    }
    override fun getItemCount(): Int {
        return entries.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val titleTextView = itemView.findViewById<TextView>(R.id.entry_title)
        private val textTextView = itemView.findViewById<TextView>(R.id.entry_text)

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(entry: DisplayEntry) {
            titleTextView.text = entry.entryTitle
            textTextView.text = entry.entryInfo

        }

        // No need since we are not clicking each of the items (in Recycler View)
        override fun onClick(v: View?) {

            val entry = entries[absoluteAdapterPosition]

        }
    }

}