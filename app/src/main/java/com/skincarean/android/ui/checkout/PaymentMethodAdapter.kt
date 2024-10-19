package com.skincarean.android.ui.checkout

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.databinding.ItemPaymentMethodBinding

class PaymentMethodAdapter(private val listPaymentMethod: List<PaymentMethodResponse>) :
    RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {
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
        val data = listPaymentMethod[position]
        holder.binding.radioButton.isClickable = false
        holder.binding.tvInputPaymentMethodDescription.text = data.description
        holder.binding.tvInputNamePaymentMethod.text = data.name
        holder.binding.radioButton.isChecked = (position == selectedPosition)
        holder.binding.layoutItemPaymentMethod.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            onItemClickCallback?.onClicked(data)
            Log.e("adapter", data.id.toString())

        }

        Glide.with(holder.binding.root)
            .load(data.image)
            .into(holder.binding.ivPaymentMethod)


    }

    override fun getItemCount(): Int {
        return listPaymentMethod.size
    }

    inner class PaymentMethodViewHolder(var binding: ItemPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onClicked(data: PaymentMethodResponse)

    }
}