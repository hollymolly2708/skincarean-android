package com.skincarean.android.ui.checkout

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
import com.skincarean.android.core.data.domain.model.cart.CartItem
import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.databinding.ItemCartCheckoutBinding
import com.skincarean.android.ui.cart.CartAdapter

class CartCheckoutAdapter:
    ListAdapter<CartItem, CartCheckoutAdapter.CartCheckoutViewHolder>(ItemDiffCallback()) {

    class ItemDiffCallback : DiffUtil.ItemCallback<CartItem>(){
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartCheckoutAdapter.CartCheckoutViewHolder {
        val view =
            ItemCartCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartCheckoutViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CartCheckoutAdapter.CartCheckoutViewHolder,
        position: Int,
    ) {
        val data = getItem(position)
        if (data != null) {

                if (data.product != null) {
                    holder.binding.tvInputCategoryName.text = data.product.categoryName
                    holder.binding.tvInputPrice.text = Utilities.numberFormat(data.total)
                    holder.binding.tvInputSize.text = data.productVariant?.size
                    holder.binding.tvInputTitleOrderProduct.text = data.product.productName
                    holder.binding.tvInputQuantityOrderProduct.text = "x${data.quantity.toString()}"
                    val uri = Uri.parse(data.productVariant?.thumbnailVariantImage)
                    Glide.with(holder.binding.root)
                        .load(uri)
                        .timeout(60000)
                        .into(holder.binding.ivOrderProduct)
                }

        }

    }


    inner class CartCheckoutViewHolder(var binding: ItemCartCheckoutBinding) :
        ViewHolder(binding.root)
}