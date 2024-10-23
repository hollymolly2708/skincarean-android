package com.skincarean.android.ui.home

import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.databinding.ItemProductBinding

class ProductAdapter(private val listPopularProduct: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    private  var onItemClickCallback: OnItemClickCallback? = null
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
        val data = listPopularProduct[position]
        holder.binding.textProductName.text = data.productName
        val uri = Uri.parse(data.thumbnailImage)

        Glide.with(holder.binding.root).load(uri).into(holder.binding.imageProduct)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onProductClickCallback(data)
        }

        setupItemView(holder, data)


    }

    override fun getItemCount(): Int {
        return listPopularProduct.size
    }

    inner class ProductViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun setupItemView(holder: ProductViewHolder, data: Product) {
        val priceAfterDiscount = holder.binding.tvTotalPrice
        val originalPrice = holder.binding.tvOriginalPrice
//
//        if (data.isPopularProduct == true) {
//            ivPopularProduct.visibility = View.VISIBLE
//        } else {
//            ivPopularProduct.visibility = View.GONE
//        }

        val layoutParams = originalPrice.layoutParams as ViewGroup.MarginLayoutParams
        val scale = originalPrice.context.resources.displayMetrics.density

        if (data.isPromo == true) {
            priceAfterDiscount.visibility = View.VISIBLE
            originalPrice.paintFlags = originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            val marginInDp = 4

            layoutParams.setMargins(
                (marginInDp * scale).toInt(), (marginInDp * scale).toInt(), 0, 0
            )
            originalPrice.textSize = 10f
            originalPrice.layoutParams = layoutParams
        } else {
            layoutParams.setMargins(
                (0), 0, 0, 0
            )
            originalPrice.textSize = 14f
            priceAfterDiscount.visibility = View.GONE
            originalPrice.visibility = View.VISIBLE

        }


    }


}