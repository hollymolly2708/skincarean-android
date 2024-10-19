package com.skincarean.android.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.databinding.ItemOrderBinding

class OrderAdapter(private val listOrderResponse: List<OrderResponse>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OrderAdapter.OrderViewHolder {
        val view = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val orderResponse = listOrderResponse[position]

        holder.binding.tvInputOrderStatus.text = orderResponse.orderStatus
        holder.binding.tvTotalPrice.text = Utilities.numberFormat(orderResponse.finalPrice)
        holder.binding.rvOrderProduct.adapter = OrderProductAdapter(orderResponse.orderItems)
        holder.binding.rvOrderProduct.layoutManager = LinearLayoutManager(holder.itemView.context)

        customBindView(holder, orderResponse)

    }

    override fun getItemCount(): Int {
        return listOrderResponse.size
    }

    inner class OrderViewHolder(var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun customBindView(holder: OrderAdapter.OrderViewHolder, data: OrderResponse) {
        if (data.orderStatus == "Menunggu pembayaran") {
            holder.binding.tvInputOrderStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )

        }

        if (data.orderStatus == "Selesai") {
            holder.binding.tvInputOrderStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.lime_green
                )
            )
        }
    }
}