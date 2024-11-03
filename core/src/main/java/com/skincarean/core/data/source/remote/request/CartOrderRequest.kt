package com.skincarean.android.core.data.source.remote.request

data class CartOrderRequest(
    val paymentMethodId: Int,
    val description: String,
    val shippingAddress: String,
)
