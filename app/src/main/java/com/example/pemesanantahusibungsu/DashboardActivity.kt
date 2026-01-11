package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val rv = findViewById<RecyclerView>(R.id.rvProduct)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Data produk
        val products = listOf(
            Product("Tahu Putih", "Tahu segar tanpa pewarna", 5000, R.drawable.tahuputih),
            Product("Tahu Kuning", "Tahu khas warna kuning", 6000, R.drawable.tahukuning),
            Product("Tahu Goreng", "Tahu goreng gurih", 7000, R.drawable.tahugoreng),
            Product("Tahu Susu", "Tahu lembut berbahan susu", 8000, R.drawable.tahususu),
            Product("Tahu Crispy", "Tahu renyah dan gurih", 7000, R.drawable.tahucrispy),
            Product("Tahu Bulat", "Tahu bulat khas", 6000, R.drawable.tahubulat),
            Product("Tahu Bakso", "Tahu isi bakso", 9000, R.drawable.tahubakso),
            Product("Tahu Isi", "Tahu dengan isian sayur", 7000, R.drawable.tahuisi),
            Product("Kerak Tahu", "Olahan tahu kering", 5000, R.drawable.keraktahu),
            Product("Kembang Tahu", "Tahu lembaran khas", 10000, R.drawable.kembangtahu)
        )

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ProductAdapter(this, products)

        // Bottom navigation listener
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_dashboard -> {
                    // Tetap di dashboard
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_orders -> {
                    startActivity(Intent(this, OrdersActivity::class.java))
                    true
                }
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
