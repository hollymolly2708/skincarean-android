package com.skincarean.android.core.data.domain.usecase.order


import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.core.Resource

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

    override fun getAllOrders(callback: (Resource<List<Order>>) -> Unit) {
        orderRepository.getAllOrders(callback)
    }

    override fun getDetailOrder(orderId: String, callback: (Resource<DetailOrder>) -> Unit) {
        orderRepository.getDetailOrder(orderId, callback)
    }

    override fun getAllCompleteOrder(callback: (Resource<List<Order>>) -> Unit) {
        orderRepository.getAllCompleteOrder(callback)
    }

    override fun getAllPendingOrder(callback: (Resource<List<Order>>) -> Unit) {
        orderRepository.getAllPendingOrder(callback)
    }

    override fun getAllCancelOrder(callback: (Resource<List<Order>>) -> Unit) {
        orderRepository.getAllCancelOrder(callback)
    }
}