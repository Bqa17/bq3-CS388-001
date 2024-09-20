package com.example.wishlistproject

import WishlistItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class itemAdapter(
    private val data: MutableList<WishlistItem>,
    private val onItemLongClickListener: MainActivity
) : RecyclerView.Adapter<itemAdapter.ViewHolder>() {

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_item_name)
        val priceTextView: TextView = view.findViewById(R.id.tv_price)
        val urlTextView: TextView = view.findViewById(R.id.tv_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = item.price
        holder.urlTextView.text = item.url

        // Handle long-click event
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener.onItemLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
