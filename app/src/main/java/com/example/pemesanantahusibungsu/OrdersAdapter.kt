package com.example.pemesanantahusibungsu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrdersAdapter(private val orders: List<OrderItem>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgOrder: ImageView = itemView.findViewById(R.id.imgOrder)
        val tvName: TextView = itemView.findViewById(R.id.tvOrderName)
        val tvQty: TextView = itemView.findViewById(R.id.tvOrderQty)
        val tvPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.imgOrder.setImageResource(order.image)
        holder.tvName.text = order.name
        holder.tvQty.text = "Qty: ${order.quantity}"
        holder.tvPrice.text = "Rp ${order.price * order.quantity}"
    }

    override fun getItemCount(): Int = orders.size
}
