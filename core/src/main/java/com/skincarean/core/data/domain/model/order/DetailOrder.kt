package com.skincarean.android.core.data.domain.model.order

import com.skincarean.android.core.data.domain.model.payment.Payment
import java.math.BigDecimal

data class DetailOrder(
    val shippingCost: BigDecimal? = null,
    val orderId: String? = null,
    val orderStatus: String? = null,
    val finalPrice: BigDecimal? = null,
    val description: String? = null,
    val shippingAddress: String? = null,
    val tax: BigDecimal? = null,
    val payment: Payment? = null,
    val orderItems: List<OrderItem?>? = null,
)