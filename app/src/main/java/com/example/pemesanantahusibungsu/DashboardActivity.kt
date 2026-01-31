package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Ambil data username dari intent Login/Register
        val username = intent.getStringExtra("username") ?: "Pengguna"

        // TextView Selamat Datang
        tvWelcome = findViewById(R.id.tv_welcome)
        tvWelcome.text = "Selamat datang, $username"

        // =========================
        // RECYCLER VIEW PRODUK
        // =========================
        val recyclerView = findViewById<RecyclerView>(R.id.rv_products)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(getProducts())

        // =========================
        // BOTTOM NAVIGATION
        // =========================
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> true
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

    // =========================
    // DATA PRODUK
    // =========================
    private fun getProducts() = listOf(
        Product("Tahu Goreng","Isi 10 pcs","Rp 5.000",R.drawable.tahu_goreng),
        Product("Tahu Putih","Isi 10 pcs","Rp 2.500",R.drawable.tahu_putih),
        Product("Tahu Bulat","Isi 10 pcs","Rp 10.000",R.drawable.tahu_bulat),
        Product("Tahu Bakso","Isi 10 pcs","Rp 10.000",R.drawable.tahu_bakso),
        Product("Tahu Crispy","Isi 10 pcs","Rp 7.000",R.drawable.tahu_crispy),
        Product("Tahu Isi","Isi 10 pcs","Rp 10.000",R.drawable.tahu_isi),
        Product("Kerak Tahu","Isi 1 porsi","Rp 8.000",R.drawable.kerak_tahu),
        Product("Tahu Susu","Isi 10 pcs","Rp 7.000",R.drawable.tahu_susu),
        Product("Susu Tahu","250 ml","Rp 5.000",R.drawable.susu_tahu),
        Product("Kembang Tahu","Isi 10 lembar","Rp 15.000",R.drawable.kembang_tahu)
    )

    // =========================
    // DATA CLASS PRODUK
    // =========================
    data class Product(
        val name: String,
        val desc: String,
        val price: String,
        val image: Int
    )

    // =========================
    // ADAPTER PRODUK
    // =========================
    inner class ProductAdapter(private val list: List<Product>) :
        RecyclerView.Adapter<ProductAdapter.VH>() {

        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            val img: ImageView = v.findViewById(R.id.iv_product)
            val name: TextView = v.findViewById(R.id.tv_name)
            val desc: TextView = v.findViewById(R.id.tv_description)
            val price: TextView = v.findViewById(R.id.tv_price)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val product = list[position]
            holder.name.text = product.name
            holder.desc.text = product.desc
            holder.price.text = product.price
            holder.img.setImageResource(product.image)

            holder.itemView.setOnClickListener {
                val intent = Intent(this@DashboardActivity, DetailProductActivity::class.java)
                intent.putExtra("name", product.name)
                intent.putExtra("desc", product.desc)
                intent.putExtra("price", product.price)
                intent.putExtra("image", product.image)
                startActivity(intent)
            }
        }

        override fun getItemCount() = list.size
    }
}
