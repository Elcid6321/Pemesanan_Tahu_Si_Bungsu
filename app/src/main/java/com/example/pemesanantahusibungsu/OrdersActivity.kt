package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.TextView

class OrdersActivity : AppCompatActivity() {

    private val orders = mutableListOf<OrderItem>() // dummy orders
    private lateinit var adapter: OrdersAdapter
    private lateinit var tvTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        tvTotal = findViewById(R.id.tvOrdersTotal)
        val rv = findViewById<RecyclerView>(R.id.rvOrders)

        // Tambahkan dummy orders
        orders.add(OrderItem("Tahu Putih", 5000, 1, R.drawable.tahuputih))
        orders.add(OrderItem("Tahu Kuning", 6000, 2, R.drawable.tahukuning))
        orders.add(OrderItem("Tahu Goreng", 7000, 1, R.drawable.tahugoreng))
        orders.add(OrderItem("Tahu Crispy", 7000, 3, R.drawable.tahucrispy))
        orders.add(OrderItem("Tahu Susu", 8000, 2, R.drawable.tahususu))

        adapter = OrdersAdapter(orders)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        updateTotal()

        // Bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_dashboard -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_orders -> true // tetap di halaman ini
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun updateTotal() {
        val total = orders.sumOf { it.price * it.quantity }
        tvTotal.text = "Total: Rp $total"
    }
}
