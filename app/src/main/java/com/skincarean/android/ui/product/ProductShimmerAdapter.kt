package com.skincarean.android.ui.product

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
import com.skincarean.android.databinding.ItemProductShimmerBinding

class ProductShimmerAdapter :
    RecyclerView.Adapter<ProductShimmerAdapter.ProductViewHolder>() {


    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductShimmerAdapter.ProductViewHolder {
        val view =
            ItemProductShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(
        holder: ProductShimmerAdapter.ProductViewHolder,
        position: Int,
    ) {

    }

    inner class ProductViewHolder(var binding: ItemProductShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

}