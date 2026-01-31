package com.example.pemesanantahusibungsu

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvCart = findViewById(R.id.rv_cart)
        tvTotal = findViewById(R.id.tv_total_amount)
        btnCheckout = findViewById(R.id.btn_checkout) // pastikan ada di layout

        // Sample cart items
        val cartList = mutableListOf(
            CartItem("Tahu Goreng", 5000, 1, R.drawable.tahu_goreng),
            CartItem("Tahu Bulat", 10000, 2, R.drawable.tahu_bulat)
        )

        val adapter = CartAdapter(cartList) {
            updateTotal(cartList)
        }

        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = adapter

        updateTotal(cartList)

        // ===== CHECKOUT BUTTON =====
        btnCheckout.setOnClickListener {
            val total = cartList.sumOf { it.price * it.qty }
            showCustomPaymentDialog(total)
        }

        // ===== BOTTOM NAV =====
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_cart

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.nav_cart -> true
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

    private fun updateTotal(list: List<CartItem>) {
        val total = list.sumOf { it.price * it.qty }
        tvTotal.text = "Rp $total"
    }

    // =========================
    // CUSTOM PAYMENT DIALOG
    // =========================
    private fun showCustomPaymentDialog(totalAmount: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_payment)
        dialog.setCancelable(false)

        val tvTotal = dialog.findViewById<TextView>(R.id.tv_total)
        val btnClose = dialog.findViewById<ImageView>(R.id.btn_close)
        val btnPay = dialog.findViewById<Button>(R.id.btn_pay)
        val rgPayment = dialog.findViewById<RadioGroup>(R.id.rg_payment)

        tvTotal.text = "Total Rp $totalAmount"

        btnClose.setOnClickListener {
            dialog.dismiss() // batal tetap di halaman cart
        }

        btnPay.setOnClickListener {
            val selectedPayment = when (rgPayment.checkedRadioButtonId) {
                R.id.rb_dana -> "Dana"
                R.id.rb_bank -> "Bank"
                else -> "Dana"
            }

            Toast.makeText(this, "Bayar dengan $selectedPayment", Toast.LENGTH_SHORT).show()

            // pindah ke dashboard
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            dialog.dismiss()
        }

        dialog.show()
    }

    // =========================
    // DATA CLASS
    // =========================
    data class CartItem(
        val name: String,
        val price: Int,
        var qty: Int,
        val image: Int
    )

    // =========================
    // ADAPTER (SATU FILE)
    // =========================
    class CartAdapter(
        private val list: MutableList<CartItem>,
        private val onUpdate: () -> Unit
    ) : RecyclerView.Adapter<CartAdapter.VH>() {

        class VH(v: View) : RecyclerView.ViewHolder(v) {
            val img: ImageView = v.findViewById(R.id.iv_product)
            val name: TextView = v.findViewById(R.id.tv_name)
            val price: TextView = v.findViewById(R.id.tv_price)
            val qty: TextView = v.findViewById(R.id.tv_qty)
            val plus: TextView = v.findViewById(R.id.btn_plus)
            val minus: TextView = v.findViewById(R.id.btn_minus)
            val remove: TextView = v.findViewById(R.id.tv_remove)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = list[position]

            holder.name.text = item.name
            holder.price.text = "Rp ${item.price}"
            holder.qty.text = item.qty.toString()
            holder.img.setImageResource(item.image)

            holder.plus.setOnClickListener {
                item.qty++
                holder.qty.text = item.qty.toString()
                onUpdate()
            }

            holder.minus.setOnClickListener {
                if (item.qty > 1) {
                    item.qty--
                    holder.qty.text = item.qty.toString()
                    onUpdate()
                }
            }

            holder.remove.setOnClickListener {
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, list.size)
                onUpdate()
            }
        }

        override fun getItemCount() = list.size
    }
}
