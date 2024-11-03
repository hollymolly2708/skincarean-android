package com.skincarean.android.core.data.source.remote.response.cart

import com.google.gson.annotations.SerializedName
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import java.math.BigDecimal

data class CartItemResponse(

    @field:SerializedName("total")
    val total: BigDecimal? = null,

    @field:SerializedName("product")
    val product: CartProductResponse? = null,

    @field:SerializedName("quantity")
    val quantity: Long? = null,

    @field:SerializedName("isActive")
    val isActive: Boolean? = null,

    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("productVariant")
    val productVariant : CartProductVariantResponse? = null
)
