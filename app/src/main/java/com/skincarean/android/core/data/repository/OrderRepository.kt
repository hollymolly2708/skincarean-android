package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.OrderRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest

class OrderRepository private constructor(private val orderRemoteDataSource: OrderRemoteDataSource) {
    companion object {
        @Volatile
        var INSTANCE: OrderRepository? = null
        fun getInstance(orderRemoteDataSource: OrderRemoteDataSource): OrderRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = OrderRepository(orderRemoteDataSource)
                }
            }
            return INSTANCE!!
        }
    }

    fun directlyOrder(directlyOrderRequest: DirectlyOrderRequest, callback: (Any) -> Unit) {
        orderRemoteDataSource.directlyOrder(directlyOrderRequest, callback)
    }

    fun getAllOrders(callback: (Any) -> Unit) {
        orderRemoteDataSource.getAllOrder(callback)
    }

    fun getAllCompleteOrders(callback: (Any) -> Unit) {

        orderRemoteDataSource.getAllCompleteOrders(callback)
    }
    fun  getAllPendingOrders(callback: (Any) -> Unit){
        orderRemoteDataSource.getAllPendingOrders(callback)
    }
}