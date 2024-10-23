package com.skincarean.android.core.data.domain.usecase.order

import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.Resource
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest

interface OrderUseCase {
    fun directlyOrder(
        directlyOrderRequest: DirectlyOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    )

    fun orderFromCart(
        cartOrderRequest: CartOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    )
}