package com.skincarean.android.ui.order

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.source.remote.response.order.OrderItemResponse
import com.skincarean.android.databinding.ItemOrderProductBinding

class OrderProductAdapter(private val listOrderProductResponse: List<OrderItemResponse?>?) :
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
        if (listOrderProductResponse != null) {
            val orderProductResponse = listOrderProductResponse[position]
            holder.binding.tvInputTitleOrderProduct.text =
                orderProductResponse?.product?.productName
            holder.binding.tvInputQuantityOrderProduct.text =
                "${orderProductResponse?.quantity.toString()}"
            holder.binding.tvInputCategoryName.text = orderProductResponse?.product?.categoryName
            holder.binding.tvInputSize.text = orderProductResponse?.product?.size
            val uri = Uri.parse(orderProductResponse?.product?.thumbnailImage)
            Glide.with(holder.binding.root)
                .load(uri)
                .into(holder.binding.ivOrderProduct)
            holder.binding.tvInputPrice.text = Utilities.numberFormat(orderProductResponse?.price)
        }
    }

    override fun getItemCount(): Int {
        return listOrderProductResponse!!.size
    }

    inner class OrderProductViewHolder(var binding: ItemOrderProductBinding) :
        ViewHolder(binding.root)
}