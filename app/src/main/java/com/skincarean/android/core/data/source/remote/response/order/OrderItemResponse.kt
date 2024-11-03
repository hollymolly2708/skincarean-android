package com.skincarean.android.core.data.source.remote.response.order

import com.google.gson.annotations.SerializedName
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import java.math.BigDecimal


data class OrderItemResponse(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("expiredAt")
    val expiredAt: String? = null,

    @field:SerializedName("product")
    val product: OrderProductResponse? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("price")
    val price: BigDecimal? = null,

    @field:SerializedName("productVariant")
    val productVariant: OrderProductVariantResponse? = null,
)
