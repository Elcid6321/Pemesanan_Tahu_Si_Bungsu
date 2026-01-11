package com.example.pemesanantahusibungsu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity(), CartAdapter.CartListener {

    private val cartItems = mutableListOf<CartItem>()
    private lateinit var adapter: CartAdapter
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button
    private lateinit var btnAddMore: Button
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        tvTotal = findViewById(R.id.tvTotal)
        btnCheckout = findViewById(R.id.btnCheckout)
        btnAddMore = findViewById(R.id.btnAddMore)
        bottomNav = findViewById(R.id.bottomNavigation)

        val rv = findViewById<RecyclerView>(R.id.rvCart)
        adapter = CartAdapter(cartItems, this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // Dummy data
        cartItems.add(CartItem("Tahu Putih", 5000, 1, R.drawable.tahuputih))
        cartItems.add(CartItem("Tahu Kuning", 6000, 2, R.drawable.tahukuning))
        cartItems.add(CartItem("Tahu Goreng", 7000, 1, R.drawable.tahugoreng))
        adapter.notifyDataSetChanged()
        updateTotal()

        btnAddMore.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        btnCheckout.setOnClickListener {
            showPaymentDialog()
        }

        // Bottom navigation listener
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_dashboard -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_cart -> true // tetap di cart
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

    private fun showPaymentDialog() {
        // Inflate layout dialog_payment.xml
        val dialogView = layoutInflater.inflate(R.layout.dialog_payment, null)
        val tvTotalDialog = dialogView.findViewById<TextView>(R.id.tvDialogTotal)
        val tvMethodA = dialogView.findViewById<TextView>(R.id.tvMethodA)
        val tvMethodB = dialogView.findViewById<TextView>(R.id.tvMethodB)
        val tvPay = dialogView.findViewById<TextView>(R.id.tvPay)
        val tvCancel = dialogView.findViewById<TextView>(R.id.tvCancel)

        tvTotalDialog.text = "Total: Rp ${cartItems.sumOf { it.price * it.quantity }}"

        var selectedMethod: TextView? = null

        fun selectMethod(tv: TextView) {
            selectedMethod?.background = resources.getDrawable(R.drawable.bg_circle_unselected, null)
            tv.background = resources.getDrawable(R.drawable.bg_circle_selected, null)
            selectedMethod = tv
        }

        tvMethodA.setOnClickListener { selectMethod(tvMethodA) }
        tvMethodB.setOnClickListener { selectMethod(tvMethodB) }

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        tvCancel.setOnClickListener { alertDialog.dismiss() }

        tvPay.setOnClickListener {
            if (selectedMethod == null) {
                Toast.makeText(this, "Pilih metode pembayaran dulu!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bayar via ${selectedMethod.text} berhasil!", Toast.LENGTH_SHORT).show()
                cartItems.clear()
                adapter.notifyDataSetChanged()
                updateTotal()
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    override fun onQuantityChanged() {
        updateTotal()
    }

    private fun updateTotal() {
        val total = cartItems.sumOf { it.price * it.quantity }
        tvTotal.text = "Total: Rp $total"
    }
}
