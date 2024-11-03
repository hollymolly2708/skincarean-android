package com.skincarean.android.core.data.source.remote.response.product

import com.google.gson.annotations.SerializedName

import java.math.BigDecimal

data class ProductResponse(

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

    @field:SerializedName("isPopularProduct")
    val isPopularProduct: Boolean? = null,

    @field:SerializedName("thumbnailImage")
    val thumbnailImage: String? = null,

    @field:SerializedName("firstOriginalPrice")
    val firstOriginalPrice: BigDecimal? = null,

    @field:SerializedName("firstPrice")
    val firstPrice: BigDecimal? = null,

    @field:SerializedName("firstDiscount")
    val firstDiscount: BigDecimal? = null,

    @field:SerializedName("minPrice")
    val minPrice: BigDecimal? = null,

    @field:SerializedName("maxPrice")
    val maxPrice: BigDecimal? = null


    )
