package com.skincarean.android.ui.product.detail

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skincarean.android.R
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.databinding.ItemProductImageBinding

//class ProductItemImageAdapter(private val listProductImage: List<ProductImageItem?>?) :
//    RecyclerView.Adapter<ProductItemImageAdapter.ProductImageViewHolder>() {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): ProductItemImageAdapter.ProductImageViewHolder {
//        val view = ProductImageViewHolder(
//            ItemProductImageBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//        return view
//    }
//
//    override fun onBindViewHolder(
//        holder: ProductItemImageAdapter.ProductImageViewHolder,
//        position: Int,
//    ) {
//        if (listProductImage != null) {
//            val productImage = listProductImage[position]
//            val uri = Uri.parse(productImage?.imageUrl)
//            Glide.with(holder.binding.root)
//                .load(uri)
//                .into(holder.binding.ivInputImageItem)
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        var size = 0
//        if (listProductImage != null) {
//            size = listProductImage.size
//        }
//        return size
//    }
//
//    inner class ProductImageViewHolder(val binding: ItemProductImageBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//    }
//}

class ProductItemImageAdapter : ListAdapter<ProductImageItem, ProductItemImageAdapter.ViewHolder>(DiffCallback()) {

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