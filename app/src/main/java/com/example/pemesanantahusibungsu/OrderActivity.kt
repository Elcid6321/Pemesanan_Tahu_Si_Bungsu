package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderActivity : AppCompatActivity() {

    private lateinit var rvOrders: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        rvOrders = findViewById(R.id.rv_orders)
        rvOrders.layoutManager = LinearLayoutManager(this)

        // Sample beberapa produk
        val products = listOf(
            Product("Tahu Goreng","Isi 10 pcs","Rp 5.000",R.drawable.tahu_goreng),
            Product("Tahu Putih","Isi 10 pcs","Rp 2.500",R.drawable.tahu_putih),
            Product("Tahu Bulat","Isi 10 pcs","Rp 10.000",R.drawable.tahu_bulat),
            Product("Tahu Bakso","Isi 10 pcs","Rp 10.000",R.drawable.tahu_bakso)
        )

        val adapter = OrderAdapter(products)
        rvOrders.adapter = adapter

        // ===== BOTTOM NAV =====
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_order

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
                R.id.nav_order -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // =========================
    // DATA CLASS & ADAPTER SATU FILE
    // =========================
    data class Product(
        val name: String,
        val desc: String,
        val price: String,
        val image: Int
    )

    class OrderAdapter(private val list: List<Product>) :
        RecyclerView.Adapter<OrderAdapter.VH>() {

        class VH(v: View) : RecyclerView.ViewHolder(v) {
            val img: ImageView = v.findViewById(R.id.iv_product)
            val name: TextView = v.findViewById(R.id.tv_name)
            val desc: TextView = v.findViewById(R.id.tv_desc)
            val price: TextView = v.findViewById(R.id.tv_price)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = list[position]
            holder.name.text = item.name
            holder.desc.text = item.desc
            holder.price.text = item.price
            holder.img.setImageResource(item.image)
        }

        override fun getItemCount() = list.size
    }
}
