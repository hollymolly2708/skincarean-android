package com.skincarean.android.core.data.domain.usecase.order

import com.skincarean.android.Resource
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest

class OrderInteractor(private val orderRepository: OrderRepository) : OrderUseCase {
    override fun directlyOrder(
        directlyOrderRequest: DirectlyOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    ) {
        orderRepository.directlyOrder(directlyOrderRequest, callback)
    }

    override fun orderFromCart(
        cartOrderRequest: CartOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    ) {
        orderRepository.orderFromCart(cartOrderRequest, callback)
    }
}