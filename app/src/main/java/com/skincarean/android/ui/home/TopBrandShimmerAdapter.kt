package com.skincarean.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skincarean.android.databinding.ItemShimmerTopBrandBinding

class TopBrandShimmerAdapter() :
    RecyclerView.Adapter<TopBrandShimmerAdapter.TopBrandShimmerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TopBrandShimmerAdapter.TopBrandShimmerViewHolder {
        val view =
            ItemShimmerTopBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopBrandShimmerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TopBrandShimmerAdapter.TopBrandShimmerViewHolder,
        position: Int,
    ) {

    }

    override fun getItemCount(): Int {
       return 20
    }

    inner class TopBrandShimmerViewHolder(var binding: ItemShimmerTopBrandBinding) :
        ViewHolder(binding.root)
}