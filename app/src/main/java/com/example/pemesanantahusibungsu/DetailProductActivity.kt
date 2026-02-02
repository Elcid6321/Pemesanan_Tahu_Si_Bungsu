package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val img = findViewById<ImageView>(R.id.img_product)
        val name = findViewById<TextView>(R.id.tv_name)
        val desc = findViewById<TextView>(R.id.tv_desc)
        val price = findViewById<TextView>(R.id.tv_price)
        val btnCart = findViewById<Button>(R.id.btn_add_cart)
        val tvBack = findViewById<TextView>(R.id.btn_back)

        // === TERIMA DATA ===
        val productName = intent.getStringExtra("name") ?: ""
        val productDesc = intent.getStringExtra("desc") ?: ""
        val productPrice = intent.getStringExtra("price") ?: ""
        val productImage = intent.getIntExtra("image", 0)

        img.setImageResource(productImage)
        name.text = productName
        desc.text = productDesc
        price.text = productPrice

        // === TAMBAH KE KERANJANG ===
        btnCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("name", productName)
            intent.putExtra("price", productPrice)
            intent.putExtra("image", productImage)
            startActivity(intent)

            Toast.makeText(this, "Ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }

        tvBack.setOnClickListener { finish() }

        // === BOTTOM NAV ===
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java)); true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java)); true
                }
                R.id.nav_order -> {
                    startActivity(Intent(this, OrderActivity::class.java)); true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java)); true
                }
                else -> false
            }
        }
    }
}
