package com.skincarean.android.ui.order

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.order.OrderItem
import com.skincarean.android.databinding.ItemOrderProductBinding

class OrderProductAdapter(private val listOrderItems: List<OrderItem?>?) :
    RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>() {
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
        if (listOrderItems != null) {
            val orderProductItem = listOrderItems[position]
            holder.binding.tvInputTitleOrderProduct.text =
                orderProductItem?.product?.productName
            holder.binding.tvInputQuantityOrderProduct.text =
                "${orderProductItem?.quantity.toString()}"
            holder.binding.tvInputCategoryName.text = orderProductItem?.product?.categoryName
            holder.binding.tvInputSize.text = orderProductItem?.product?.size
            val uri = Uri.parse(orderProductItem?.product?.thumbnailImage)
            Glide.with(holder.binding.root)
                .load(uri)
                .into(holder.binding.ivOrderProduct)
            holder.binding.tvInputPrice.text = Utilities.numberFormat(orderProductItem?.price)
        }
    }

    override fun getItemCount(): Int {
        return listOrderItems!!.size
    }

    inner class OrderProductViewHolder(var binding: ItemOrderProductBinding) :
        ViewHolder(binding.root)
}