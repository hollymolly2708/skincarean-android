package com.skincarean.android.ui.cart

import android.annotation.SuppressLint
import android.util.Log
import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.cart.CartItem
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.databinding.ItemProductCartBinding

class CartAdapter(private val list: List<CartItem?>?) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
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
        if (list != null) {
            val data = list[position]
            if (data != null) {
                if (data.product != null) {
                    holder.binding.tvInputTitleProduct.text = data.product.productName
                    holder.binding.tvInputBrandName.text = data.product.brandName
                    holder.binding.tvInputPrice.text = Utilities.numberFormat(data.total)
                    holder.binding.tvInputCategory.text = data.product.categoryName
                }
                holder.binding.tvInputQuantity.text = "x${data.quantity.toString()}"
            }


            holder.binding.btnPlus.setOnClickListener {

                if (data != null) {
                    onItemClickCallback?.onPlusClicked(data.id!!)
                }


            }

            holder.binding.btnMinus.setOnClickListener {
                if (data != null) {
                    onItemClickCallback?.onMinusClicked(data.id!!)
                }
            }

            holder.binding.ivTrashCartItem.setOnClickListener {
                if (data != null) {
                    onItemClickCallback?.onTrashCartItemClicked(data.id!!)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return list!!.size

    }

    inner class CartViewHolder(var binding: ItemProductCartBinding) : ViewHolder(binding.root)


}