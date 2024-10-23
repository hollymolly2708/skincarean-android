package com.skincarean.android.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.databinding.ItemTopBrandBinding

class TopBrandAdapter(private val listTopBrand: List<BrandResponse>) :
    RecyclerView.Adapter<TopBrandAdapter.TopBrandViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
     fun setUpOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TopBrandAdapter.TopBrandViewHolder {
        val view = TopBrandViewHolder(
            ItemTopBrandBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return view
    }

    override fun onBindViewHolder(holder: TopBrandAdapter.TopBrandViewHolder, position: Int) {
        val data = listTopBrand[position]
        val brandLogoUri = Uri.parse(data.brandLogo)
        Glide.with(holder.binding.root)

            .load(brandLogoUri)
            .centerCrop()
            .into(holder.binding.ivBrand)
        holder.binding.layoutTopBrand?.setOnClickListener {
            onItemClickCallback?.onTopBrandClickCallback(data)
        }
    }

    override fun getItemCount(): Int {
        if (listTopBrand.size <= 10) {
            return listTopBrand.size
        } else {
            return listTopBrand.size
        }
//       return listTopBrand.size

    }

    inner class TopBrandViewHolder(var binding: ItemTopBrandBinding) :
        RecyclerView.ViewHolder(binding.root)
}