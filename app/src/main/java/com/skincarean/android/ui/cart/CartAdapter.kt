package com.skincarean.android.ui.cart

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.cart.CartItem
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.databinding.ItemProductCartBinding

class CartAdapter :
    ListAdapter<CartItem, CartAdapter.CartViewHolder>(ItemDiffCallback()) {
    class ItemDiffCallback() : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        return CartViewHolder(
            ItemProductCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val list = getItem(position)
        if (list != null) {

            holder.binding.apply {
                tvInputTitleProduct.text = list.product?.productName
                tvInputBrandName.text = list.product?.brandName
                tvInputPrice.text = Utilities.numberFormat(list.total)
                tvInputCategory.text = list.product?.categoryName

                if (list.productVariant?.thumbnailVariantImage != null) {
                    val uri = Uri.parse(list.productVariant?.thumbnailVariantImage)
                    Glide.with(holder.binding.root)
                        .load(uri)
                        .timeout(60000)
                        .placeholder(R.drawable.ic_loading)
                        .into(holder.binding.ivProductCart)
                    tvInputQuantity.text = "x${list.quantity.toString()}"

                }else{
                    val uri = Uri.parse(list.product?.thumbnailImage)
                    Glide.with(holder.binding.root)
                        .load(uri)
                        .timeout(60000)
                        .placeholder(R.drawable.ic_loading)
                        .into(holder.binding.ivProductCart)
                    tvInputQuantity.text = "x${list.quantity.toString()}"
                }

            }


            setUIByActiveCartItem(holder, list.isActive)


            holder.binding.apply {
                btnPlus.setOnClickListener {
                    onItemClickCallback?.onPlusClicked(list.id!!)
                }

                btnMinus.setOnClickListener {
                    onItemClickCallback?.onMinusClicked(list.id!!)
                }

                ivTrashCartItem.setOnClickListener {
                    onItemClickCallback?.onTrashCartItemClicked(list.id!!)
                }
                ivInputIsActive.setOnClickListener {
                    onItemClickCallback?.onCheckBoxCartItemClicked(list.id!!)
                }
            }

        }


    }


    private fun setUIByActiveCartItem(holder: CartAdapter.CartViewHolder, isActive: Boolean?) {

        if (isActive == null || isActive == false) {
            holder.binding.ivInputIsActive.setImageResource(R.drawable.ic_uncheck_box)
        } else {
            holder.binding.ivInputIsActive.setImageResource(R.drawable.ic_check_box)
        }
    }

    inner class CartViewHolder(var binding: ItemProductCartBinding) : ViewHolder(binding.root)


}