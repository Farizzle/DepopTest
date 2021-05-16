package com.example.depoptest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.depoptest.R
import com.example.depoptest.data.remote.response.model.PicturesData
import kotlinx.android.synthetic.main.list_item_product.view.*
import javax.inject.Inject

class ProductImagesAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<PicturesData, ProductImagesAdapter.ProductImagesViewHolder>(ProductImagesDiffChecker()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImagesViewHolder {
        return ProductImagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_product_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(picturesData: PicturesData) {
            itemView.apply {
                val productImage = picturesData.largeThumbnail
                glide.load(productImage).apply(RequestOptions().override(1024, 1024))
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(product_image)
            }
        }
    }

    class ProductImagesDiffChecker : DiffUtil.ItemCallback<PicturesData>() {
        override fun areItemsTheSame(oldItem: PicturesData, newItem: PicturesData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PicturesData, newItem: PicturesData): Boolean {
            return oldItem == newItem
        }
    }

}