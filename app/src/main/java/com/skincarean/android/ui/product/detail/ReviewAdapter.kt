package com.skincarean.android.ui.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skincarean.android.R
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.databinding.ItemReviewBinding

class ReviewAdapter(private val listReviewResponse: List<ReviewResponse>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReviewAdapter.ReviewViewHolder {
        val view = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listReviewResponse.size
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        val data = listReviewResponse[position]
        setupItemView(holder, data)

    }

    inner class ReviewViewHolder(var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setupItemView(holder: ReviewViewHolder, data: ReviewResponse) {

        if (data.isRecommended == true) {
            holder.binding.tvIsRecommended.text =
                "${data.fullNameUser} does recommended this product"
            holder.binding.tvIsRecommended.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_like
                )
            )
            holder.binding.tvIsRecommended.setBackgroundResource(R.drawable.background_recommended_review)
        } else {
            holder.binding.tvIsRecommended.text =
                "${data.fullNameUser} doesn't recommended this product"
            holder.binding.tvIsRecommended.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_unlike
                )
            )
            holder.binding.tvIsRecommended.setBackgroundResource(R.drawable.background_unrecommended_review)
        }

        holder.binding.tvInputUsagePeriod.text = data.usagePeriod
        holder.binding.tvName.text = data.fullNameUser
        holder.binding.tvReview.text = data.review

        if (!holder.binding.ivUserReview.equals(null)) {
            Glide.with(holder.binding.root)
                .load(data.photoProfileUser)
                .into(holder.binding.ivUserReview)
        }

        if (data.rating != null) {
            holder.binding.ratingBar.rating = data.rating.toFloat()
            holder.binding.ratingText.text = data.rating.toString()
        }


    }
}