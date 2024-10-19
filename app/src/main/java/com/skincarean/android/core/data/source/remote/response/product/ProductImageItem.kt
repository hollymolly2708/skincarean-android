package com.skincarean.android.core.data.source.remote.response.product

import com.google.gson.annotations.SerializedName



data class ProductImageItem(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
