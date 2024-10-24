package com.skincarean.android.core.data.domain.model.cart

import com.skincarean.android.core.data.domain.model.product.Product
import java.math.BigDecimal

data class CartItem(
    val total: BigDecimal? = null,
    val product: Product? = null,
    val quantity: Long? = null,
    val id: Long? = null,
)