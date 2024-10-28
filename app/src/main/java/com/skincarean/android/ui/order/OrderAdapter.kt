package com.skincarean.android.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.databinding.ItemOrderBinding
import com.skincarean.android.ui.checkout.PaymentMethodAdapter

class OrderAdapter(private val listOrderResponse: List<Order?>?) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OrderAdapter.OrderViewHolder {
        val view = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        if (listOrderResponse != null) {
            val orderResponse = listOrderResponse[position]
            if (orderResponse != null) {
                holder.binding.apply {
                    tvInputOrderStatus.text = orderResponse.orderStatus
                    tvInputTotalPrice.text = Utilities.numberFormat(orderResponse.finalPrice)
                    rvOrderProduct.adapter =
                        OrderProductAdapter(orderResponse.orderItems)
                    rvOrderProduct.layoutManager =
                        LinearLayoutManager(holder.itemView.context)

                    customBindView(holder, orderResponse)



                    rvOrderProduct.isClickable = false
                    rvOrderProduct.isNestedScrollingEnabled = false



                }
                holder.binding.layoutItemOrder.setOnClickListener {
                    onItemClickCallback?.onOrderClickCallback(orderResponse)
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return listOrderResponse!!.size
    }

    inner class OrderViewHolder(var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun customBindView(holder: OrderAdapter.OrderViewHolder, data: Order) {
        if (data.orderStatus == "Menunggu pembayaran") {
            holder.binding.tvInputOrderStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.red
                )
            )

        }

        if (data.orderStatus == "Selesai") {
            holder.binding.tvInputOrderStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.lime_green
                )
            )
        }
    }
}