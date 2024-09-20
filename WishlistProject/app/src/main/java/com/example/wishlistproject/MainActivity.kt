package com.example.wishlistproject

import WishlistItem
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var data: MutableList<WishlistItem> // List of wishlist items
    private lateinit var adapter: itemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        data = mutableListOf()
        adapter = itemAdapter(data,this)

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rv_wishlist)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Find UI elements
        val itemNameEditText: EditText = findViewById(R.id.et_item_name)
        val priceEditText: EditText = findViewById(R.id.et_price)
        val urlEditText: EditText = findViewById(R.id.et_url)
        val submitButton: Button = findViewById(R.id.btn_submit)

        submitButton.setOnClickListener {
            val name = itemNameEditText.text.toString()
            val price = priceEditText.text.toString()
            val url = urlEditText.text.toString()

            if (name.isNotEmpty() && url.isNotEmpty()) {
                val newItem = WishlistItem(name, price, url)
                data.add(newItem)
                adapter.notifyItemInserted(data.size - 1)

                // Clear input fields
                itemNameEditText.text.clear()
                priceEditText.text.clear()
                urlEditText.text.clear()

                recyclerView.scrollToPosition(data.size - 1)
            } else {
                Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onItemLongClick(position: Int) {
        data.removeAt(position)
        adapter.notifyItemRemoved(position)
        Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show()
    }
}
