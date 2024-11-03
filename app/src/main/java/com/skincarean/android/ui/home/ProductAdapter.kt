package com.skincarean.android.ui.home

import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.databinding.ItemProductBinding
import java.math.BigDecimal

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ItemDiffCallback()) {

    class ItemDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductAdapter.ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductAdapter.ProductViewHolder,
        position: Int,
    ) {
        val data = getItem(position)
        holder.binding.textProductName.text = data.productName
        val uri = Uri.parse(data.thumbnailImage)

        Glide.with(holder.binding.root)
            .load(uri)
            .timeout(60000)
            .into(holder.binding.imageProduct)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onProductClickCallback(data)
        }

        setupItemView(holder, data)


    }

    inner class ProductViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun setupItemView(holder: ProductViewHolder, data: Product) {
        val priceAfterDiscount = holder.binding.tvTotalPrice
        val originalPrice = holder.binding.tvOriginalPrice
        val discount = holder.binding.tvInputDiscount

        priceAfterDiscount.text = Utilities.numberFormat(data.firstPrice)
        originalPrice.text = Utilities.numberFormat(data.firstOriginalPrice)
        discount.text = "-${data.firstDiscount}%"

        val layoutParams = originalPrice.layoutParams as ViewGroup.MarginLayoutParams
        val scale = originalPrice.context.resources.displayMetrics.density

        if (data.firstDiscount != null && data.firstDiscount > BigDecimal.valueOf(0)) {
            priceAfterDiscount.visibility = View.VISIBLE
            discount.visibility = View.VISIBLE

            priceAfterDiscount.setTextColor(
                ContextCompat.getColor(
                    originalPrice.context,
                    R.color.red_50
                )
            )

            originalPrice.paintFlags = originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            originalPrice.setTypeface(originalPrice.typeface, Typeface.BOLD_ITALIC)

            // Mengatur margin dan ukuran teks khusus saat promo
            val marginInDp = 4
            layoutParams.setMargins(
                (marginInDp * scale).toInt(),
                (marginInDp * scale).toInt(),
                0,
                0
            )
            originalPrice.layoutParams = layoutParams
            originalPrice.textSize = 10f
            originalPrice.setTextColor(
                ContextCompat.getColor(
                    originalPrice.context,
                    R.color.black
                )
            )
        } else {
            // Reset tampilan ke default jika tidak promo
            priceAfterDiscount.visibility = View.GONE
            discount.visibility = View.GONE

            originalPrice.paintFlags =
                originalPrice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            originalPrice.setTypeface(originalPrice.typeface, Typeface.BOLD)

            layoutParams.setMargins(0, 0, 0, 0)
            originalPrice.layoutParams = layoutParams
            originalPrice.textSize = 12f
            originalPrice.setTextColor(
                ContextCompat.getColor(
                    originalPrice.context,
                    R.color.black
                )
            )
        }
    }


}