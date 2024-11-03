package com.skincarean.android.core.data.domain.model.order

import com.skincarean.android.core.data.domain.model.product.Product
import java.math.BigDecimal

data class OrderItem(
        val product: OrderProduct? = null,
        val quantity: Int? = null,
        val price: BigDecimal? = null,
        val productVariant: OrderProductVariant? = null
)