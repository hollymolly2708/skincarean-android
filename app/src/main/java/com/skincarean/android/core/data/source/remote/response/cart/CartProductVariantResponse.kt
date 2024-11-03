package com.skincarean.android.core.data.source.remote.response.cart

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CartProductVariantResponse(
    @field:SerializedName("size")
    val size: String? = null,
    @field:SerializedName("price")
    val price: BigDecimal? = null,
    @field:SerializedName("originalPrice")
    val originalPrice: BigDecimal? = null,
    @field:SerializedName("discount")
    val discount: BigDecimal? = null,
    @field:SerializedName("id")
    val id: Long? = null,
    @field:SerializedName("thumbnailVariantImage")
    val thumbnailVariantImage: String? = null,
)
