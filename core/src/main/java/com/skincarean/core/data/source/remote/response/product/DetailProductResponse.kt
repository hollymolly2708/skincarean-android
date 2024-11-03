package com.skincarean.android.core.data.source.remote.response.product

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class DetailProductResponse(

    @field:SerializedName("brandName")
    val brandName: String? = null,

    @field:SerializedName("productId")
    val productId: String? = null,

    @field:SerializedName("isPromo")
    val isPromo: Boolean? = null,

    @field:SerializedName("categoryName")
    val categoryName: String? = null,

    @field:SerializedName("productName")
    val productName: String? = null,

    @field:SerializedName("bpomCode")
    val bpomCode: String? = null,

    @field:SerializedName("isPopularProduct")
    val isPopularProduct: Boolean? = null,

    @field:SerializedName("totalStok")
    val totalStok: Long? = null,

    @field:SerializedName("minPrice")
    val minPrice: BigDecimal? = null,

    @field:SerializedName("maxPrice")
    val maxPrice: BigDecimal? = null,

    @field:SerializedName("thumbnailImage")
    val thumbnailImage: String? = null,

    @field:SerializedName("ingredient")
    val ingredient: String? = null,

    @field:SerializedName("productDescription")
    val productDescription: String? = null,

    @field:SerializedName("productVariants")
    val productVariantResponses: List<ProductVariantResponse?>? = null,
)
