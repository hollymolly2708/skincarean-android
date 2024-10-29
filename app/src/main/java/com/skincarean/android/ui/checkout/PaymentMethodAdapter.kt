package com.skincarean.android.ui.checkout

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.android.databinding.ItemPaymentMethodBinding

class PaymentMethodAdapter :
    ListAdapter<PaymentMethod, PaymentMethodAdapter.PaymentMethodViewHolder>(ItemDiffCallback()) {
    class ItemDiffCallback : DiffUtil.ItemCallback<PaymentMethod>() {
        override fun areItemsTheSame(oldItem: PaymentMethod, newItem: PaymentMethod): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PaymentMethod, newItem: PaymentMethod): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private var selectedPosition = -1
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PaymentMethodAdapter.PaymentMethodViewHolder {
        val view =
            ItemPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentMethodViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: PaymentMethodAdapter.PaymentMethodViewHolder,
        position: Int,
    ) {
        val data = getItem(position)
        holder.binding.radioButton.isClickable = false
        holder.binding.tvInputPaymentMethodDescription.text = data.description
        holder.binding.tvInputNamePaymentMethod.text = data.name
        holder.binding.radioButton.isChecked = (position == selectedPosition)
        holder.binding.layoutItemPaymentMethod.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            onItemClickCallback?.onPaymentMethodClickCallback(data)
            Log.e("adapter", data.id.toString())

        }

        Glide.with(holder.binding.root)
            .load(data.image)
            .timeout(60000)
            .into(holder.binding.ivPaymentMethod)


    }

    inner class PaymentMethodViewHolder(var binding: ItemPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root)

}