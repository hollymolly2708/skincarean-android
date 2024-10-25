package com.skincarean.android.core.data.domain.usecase.order

import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
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

    fun getAllOrders(callback: (Resource<List<Order>>) -> Unit)
    fun getDetailOrder(orderId: String, callback: (Resource<DetailOrder>) -> Unit)
    fun getAllCompleteOrder(callback: (Resource<List<Order>>) -> Unit)
    fun getAllPendingOrder(callback: (Resource<List<Order>>) -> Unit)
    fun getAllCancelOrder(callback: (Resource<List<Order>>) -> Unit)
}