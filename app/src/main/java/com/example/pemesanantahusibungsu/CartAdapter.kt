package com.example.pemesanantahusibungsu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val listener: CartListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface CartListener {
        fun onQuantityChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgCart)
        val tvName: TextView = itemView.findViewById(R.id.tvCartName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvCartPrice)
        val tvQty: TextView = itemView.findViewById(R.id.tvCartQuantity)
        val tvPlus: TextView = itemView.findViewById(R.id.tvPlus)
        val tvMinus: TextView = itemView.findViewById(R.id.tvMinus)
        val tvRemove: TextView = itemView.findViewById(R.id.tvRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.img.setImageResource(item.image)
        holder.tvName.text = item.name
        holder.tvPrice.text = "Rp ${item.price * item.quantity}"
        holder.tvQty.text = "Qty: ${item.quantity}"

        holder.tvPlus.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            listener.onQuantityChanged()
        }

        holder.tvMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                listener.onQuantityChanged()
            }
        }

        holder.tvRemove.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
            listener.onQuantityChanged()
        }
    }
}
