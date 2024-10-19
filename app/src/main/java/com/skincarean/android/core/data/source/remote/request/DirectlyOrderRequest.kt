package com.skincarean.android.core.data.source.remote.request

data class DirectlyOrderRequest(
    val productId: String,
    val quantity: Int,
    val paymentMethodId: Int,
    val description: String,
    val shippingAddress: String
)