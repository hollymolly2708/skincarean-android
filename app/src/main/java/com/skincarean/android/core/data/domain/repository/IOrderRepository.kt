package com.skincarean.android.core.data.domain.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest

interface IOrderRepository {
    fun directlyOrder(
        directlyOrderRequest: DirectlyOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    )
    fun orderFromCart(
        cartOrderRequest: CartOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit
    )
}