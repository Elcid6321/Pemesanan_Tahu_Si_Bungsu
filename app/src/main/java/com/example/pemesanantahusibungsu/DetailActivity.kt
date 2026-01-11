package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgProduct = findViewById<ImageView>(R.id.imgDetail)
        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvDesc = findViewById<TextView>(R.id.tvDetailDesc)
        val tvPrice = findViewById<TextView>(R.id.tvDetailPrice)
        val btnOrder = findViewById<Button>(R.id.btnOrder)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Ambil data dari intent
        val name = intent.getStringExtra("NAME")
        val desc = intent.getStringExtra("DESC")
        val price = intent.getIntExtra("PRICE", 0)
        val image = intent.getIntExtra("IMAGE", 0)

        // Set ke view
        imgProduct.setImageResource(image)
        tvName.text = name
        tvDesc.text = desc
        tvPrice.text = "Harga: Rp $price"

        // Tombol PESAN → langsung masuk Cart
        btnOrder.setOnClickListener {
            Toast.makeText(this, "Pesanan $name berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Bottom navigation listener
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_dashboard -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_orders -> {
                    startActivity(Intent(this, OrdersActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
