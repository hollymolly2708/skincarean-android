package com.skincarean.android.ui.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.core.data.domain.model.product.ProductVariant
import com.skincarean.android.databinding.ItemVariantProductBinding

class ProductVariantAdapter(private val listProductVariant: List<ProductVariant?>?) :
    RecyclerView.Adapter<ProductVariantAdapter.ProductVariantViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductVariantAdapter.ProductVariantViewHolder {
        val view =
            ItemVariantProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductVariantViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductVariantAdapter.ProductVariantViewHolder,
        position: Int,
    ) {
        if (listProductVariant != null) {

            val productVariant = listProductVariant[position]
            if (productVariant?.isSelected == true) {
                holder.binding.tvInputVariant.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        holder.binding.root.context,
                        R.drawable.background_square_corners_line
                    )
                )
            } else {
                holder.binding.tvInputVariant.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        holder.binding.root.context,
                        R.drawable.background_square_corners
                    )
                )
            }
            if (productVariant != null) {
                holder.binding.tvInputVariant.text = productVariant.size
                holder.binding.tvInputVariant.setOnClickListener {
                    listProductVariant.forEach { it?.isSelected = false }
                    productVariant.isSelected = true
                    notifyDataSetChanged()
                    onItemClickCallback?.onProductVariantClickCallback(
                        productVariant
                    )
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return listProductVariant!!.size
    }

    class ProductVariantViewHolder(var binding: ItemVariantProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}