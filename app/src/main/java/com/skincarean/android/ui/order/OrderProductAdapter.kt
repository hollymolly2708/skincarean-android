package com.skincarean.android.ui.order

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.model.order.OrderItem
import com.skincarean.android.databinding.ItemOrderProductBinding

class OrderProductAdapter :
    ListAdapter<OrderItem, OrderProductAdapter.OrderProductViewHolder>(ItemDiffCallback()) {

    class ItemDiffCallback() : DiffUtil.ItemCallback<OrderItem>() {
        override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.product == newItem.product
        }

        override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.product == newItem.product
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OrderProductAdapter.OrderProductViewHolder {
        val view =
            ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: OrderProductAdapter.OrderProductViewHolder,
        position: Int,
    ) {

        val orderItems = getItem(position)
        if (orderItems != null) {
            holder.binding.tvInputTitleOrderProduct.text =
                orderItems.product?.productName
            holder.binding.tvInputQuantityOrderProduct.text =
                "${orderItems?.quantity.toString()}"
            holder.binding.tvInputCategoryName.text = orderItems.product?.categoryName
            holder.binding.tvInputSize.text = orderItems.productVariant?.size
            val uri = Uri.parse(orderItems.productVariant?.thumbnailImageVariant)
            Glide.with(holder.binding.root)
                .load(uri)
                .timeout(60000)
                .into(holder.binding.ivOrderProduct)

            holder.binding.tvInputPrice.text = Utilities.numberFormat(orderItems.price)
        }
    }

    inner class OrderProductViewHolder(var binding: ItemOrderProductBinding) :
        ViewHolder(binding.root)
}