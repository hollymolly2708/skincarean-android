package com.skincarean.android.ui.product.detail

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.skincarean.android.R
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.databinding.ItemProductImageBinding
import okhttp3.internal.http.RequestLine


class ProductItemImageAdapter :
    ListAdapter<ProductImageItem, ProductItemImageAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<ProductImageItem>() {
        override fun areItemsTheSame(
            oldItem: ProductImageItem,
            newItem: ProductImageItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductImageItem,
            newItem: ProductImageItem,
        ): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bindData(item: ProductImageItem) {
            Glide.with(binding.root)
                .load(item.imageUrl)
                .timeout(60000)
                .into(binding.ivInputImageItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolder(
            ItemProductImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
    }
}