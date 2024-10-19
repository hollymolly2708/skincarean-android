package com.skincarean.android.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.skincarean.android.core.data.source.remote.response.order.OrderItemResponse
import com.skincarean.android.core.data.source.remote.response.order.PaymentResponse
import java.math.BigDecimal

data class OrderResponse(

    @field:SerializedName("shippingCost")
    val shippingCost: Any? = null,

    @field:SerializedName("orderId")
    val orderId: String? = null,

    @field:SerializedName("orderStatus")
    val orderStatus: String? = null,

    @field:SerializedName("finalPrice")
    val finalPrice: BigDecimal? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("shippingAddress")
    val shippingAddress: String? = null,

    @field:SerializedName("tax")
    val tax: Any? = null,

    @field:SerializedName("payment")
    val payment: PaymentResponse? = null,

    @field:SerializedName("orderItems")
    val orderItems: List<OrderItemResponse?>? = null,
)




