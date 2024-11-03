package com.skincarean.android.core.data.source.remote.response.product

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ProductVariantResponse(

    @field:SerializedName("id")
    val id: Long? = null,
    @field:SerializedName("size")
    val size: String? = null,
    @field:SerializedName("stok")
    val stok: Long? = null,
    @field:SerializedName("price")
    val price: BigDecimal? = null,
    @field:SerializedName("originalPrice")
    val originalPrice: BigDecimal? = null,
    @field:SerializedName("discount")
    val discount: BigDecimal? = null,
    @field:SerializedName("thumbnailVariantImage")
    val thumbnailVariantImage: String? = null,
    @field:SerializedName("productVariantImages")
    val productVariantImages: List<ProductVariantImageResponse?>? = null,

    )
