package com.example.pemesanantahusibungsu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val context: Context,
    private val items: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvDesc: TextView = view.findViewById(R.id.tvProductDesc)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]

        holder.imgProduct.setImageResource(product.image)
        holder.tvName.text = product.name
        holder.tvDesc.text = product.desc
        holder.tvPrice.text = "Rp ${product.price}"

        // Klik item → DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("NAME", product.name)
            intent.putExtra("DESC", product.desc)
            intent.putExtra("PRICE", product.price)
            intent.putExtra("IMAGE", product.image)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
