package com.skincarean.android.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.databinding.ItemOrderBinding

class OrderAdapter :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(ItemDiffCallback()) {

    class ItemDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

    }

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
        val item = getItem(position)
        if (item != null) {


            holder.binding.apply {
                tvInputOrderStatus.text = item.orderStatus
                tvInputTotalPrice.text = Utilities.numberFormat(item.finalPrice)
                val orderItemAdapter = OrderProductAdapter()
                orderItemAdapter.submitList(item.orderItems)
                rvOrderProduct.adapter =
                  orderItemAdapter
                rvOrderProduct.layoutManager =
                    LinearLayoutManager(holder.itemView.context)

                customBindView(holder, item)



                rvOrderProduct.isClickable = false
                rvOrderProduct.isNestedScrollingEnabled = false


            }
            holder.binding.layoutItemOrder.setOnClickListener {
                onItemClickCallback?.onOrderClickCallback(item)
            }
        }


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