package com.skincarean.android.core.data.domain.model.order

import com.skincarean.android.core.data.domain.model.product.Product
import java.math.BigDecimal

data class OrderItem(
        val createdAt: String? = null,
        val expiredAt: String? = null,
        val product: Product? = null,
        val quantity: Int? = null,
        val price: BigDecimal? = null,
)