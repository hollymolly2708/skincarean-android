package com.skincarean.android.core.data.domain.model.cart

import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse
import java.math.BigDecimal

data class Cart(
    val quantity: Int? = null,
    val totalPrice: BigDecimal? = null,
    val id: Long? = null,
    val cartItems: List<CartItem?>? = null,
)
