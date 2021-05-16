package com.example.depoptest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.depoptest.R
import com.example.depoptest.data.local.model.Product
import kotlinx.android.synthetic.main.list_item_product.view.*
import javax.inject.Inject

class ProductAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffChecker()) {

    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Product) -> Unit)) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.apply {
                glide.load(product.smallThumbNail).into(product_image)
                glide.load(product.largeThumbnail).downloadOnly(1280, 1280)
                user_id.text = product.user_id.toString()
                product_description.text = product.description
                setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(product)
                    }
                }
            }
        }
    }

    class ProductDiffChecker : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

}