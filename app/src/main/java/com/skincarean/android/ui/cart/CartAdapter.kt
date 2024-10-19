package com.skincarean.android.ui.cart

import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.databinding.ItemProductCartBinding

class CartAdapter(private val list: List<CartItemResponse?>?) :
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

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        if (list != null) {
            val data = list[position]
            if (data != null) {
                if (data.product != null) {
                    holder.binding.tvInputTitleProduct.text = data.product.productName
                    holder.binding.tvInputBrandName.text = data.product.brandName
                    holder.binding.tvInputPrice.text = data.total.toString()
                    holder.binding.tvInputCategory.text = data.product.categoryName
                }
                holder.binding.tvInputQuantity.text = data.quantity.toString()
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

    interface OnItemClickCallback {
        fun onPlusClicked(cartId: Long)
        fun onMinusClicked(cartId: Long)
        fun onTrashCartItemClicked(cartId: Long)
    }
}