package com.skincarean.android.core.data.source.remote.response.order

import com.google.gson.annotations.SerializedName

data class OrderProductResponse(
    @field:SerializedName("productId")
    val productId: String? = null,
    @field:SerializedName("productName")
    val productName: String? = null,
    @field:SerializedName("thumbnailImage")
    val thumbnailImage: String? = null,
    @field:SerializedName("brandName")
    val brandName: String? = null,
    @field:SerializedName("categoryName")
    val categoryName: String? = null,
)
