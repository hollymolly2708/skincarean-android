package com.skincarean.android.core.data.source.remote.response.product

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ProductResponse(

    @field:SerializedName("brandName")
	val brandName: String? = null,

    @field:SerializedName("productId")
	val productId: String? = null,

    @field:SerializedName("originalPrice")
	val originalPrice: BigDecimal? = null,

    @field:SerializedName("discount")
	val discount: Any? = null,

    @field:SerializedName("stok")
	val stok: Int? = null,

    @field:SerializedName("isPromo")
	val isPromo: Boolean? = null,

    @field:SerializedName("categoryName")
	val categoryName: String? = null,

    @field:SerializedName("productName")
	val productName: String? = null,

    @field:SerializedName("bpomCode")
	val bpomCode: String? = null,

    @field:SerializedName("size")
	val size: String? = null,

    @field:SerializedName("price")
	val price: BigDecimal? = null,

    @field:SerializedName("isPopularProduct")
	val isPopularProduct: Any? = null,

    @field:SerializedName("thumbnailImage")
	val thumbnailImage: String? = null,

    @field:SerializedName("productImage")
    val productImage: List<ProductImageItem?>? = null,

    @field:SerializedName("productDescription")
	val productDescription: String? = null
)
