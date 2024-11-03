package com.skincarean.android.core.data.source.remote.response.review

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("photoProfileUser")
	val photoProfileUser: String? = null,

	@field:SerializedName("isRecommended")
	val isRecommended: Boolean? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("usagePeriod")
	val usagePeriod: String? = null,

	@field:SerializedName("fullNameUser")
	val fullNameUser: String? = null,

	@field:SerializedName("reviewId")
	val reviewId: Int? = null
)
