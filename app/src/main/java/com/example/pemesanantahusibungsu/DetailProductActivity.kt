package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast

class DetailProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        // ======= FIND VIEW =======
        val img = findViewById<ImageView>(R.id.img_product)
        val name = findViewById<TextView>(R.id.tv_name)
        val desc = findViewById<TextView>(R.id.tv_desc)
        val price = findViewById<TextView>(R.id.tv_price)
        val btnCart = findViewById<Button>(R.id.btn_add_cart)
        val tvBack = findViewById<TextView>(R.id.btn_back)

        // ======= TERIMA DATA DARI DASHBOARD =======
        img.setImageResource(intent.getIntExtra("image", 0))
        name.text = intent.getStringExtra("name")
        desc.text = intent.getStringExtra("desc")
        price.text = intent.getStringExtra("price")

        // ======= TOMBOL TAMBAH KE KERANJANG =======
        btnCart.setOnClickListener {
            Toast.makeText(this, "${name.text} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()

        }

        // ======= TOMBOL KEMBALI =======
        tvBack.setOnClickListener {
            finish()
        }

        // =========================
        // BOTTOM NAVIGATION
        // =========================
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_order -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
