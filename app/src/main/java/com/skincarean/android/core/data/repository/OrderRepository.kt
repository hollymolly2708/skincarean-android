package com.skincarean.android.core.data.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.repository.IOrderRepository
import com.skincarean.android.core.data.mapper.OrderMapper
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.OrderRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest

class OrderRepository private constructor(private val orderRemoteDataSource: OrderRemoteDataSource) :
    IOrderRepository {
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


    override fun getDetailOrder(orderId: String, callback: (Resource<DetailOrder>) -> Unit) {
        orderRemoteDataSource.getDetailOrder(orderId) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { orderResponse ->
                        val detailOrder = OrderMapper.orderResponseToDetailOrder(orderResponse)
                        callback(Resource.Success(detailOrder))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun getAllCompleteOrder(callback: (Resource<List<Order>>) -> Unit) {
        orderRemoteDataSource.getAllCompleteOrders { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { listOrderResponses ->
                        val listOrders =
                            OrderMapper.listOrderResponseToListOrder(listOrderResponses)
                        callback(Resource.Success(listOrders))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun getAllPendingOrder(callback: (Resource<List<Order>>) -> Unit) {
        orderRemoteDataSource.getAllPendingOrders { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { listOrderResponses ->
                        val listOrders =
                            OrderMapper.listOrderResponseToListOrder(listOrderResponses)
                        callback(Resource.Success(listOrders))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun getAllCancelOrder(callback: (Resource<List<Order>>) -> Unit) {
        orderRemoteDataSource.getAllCancelOrders { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { orderResponses ->
                        val orders = OrderMapper.listOrderResponseToListOrder(orderResponses)
                        callback(Resource.Success(orders))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        }
    }


    override fun directlyOrder(
        directlyOrderRequest: DirectlyOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    ) {
        orderRemoteDataSource.directlyOrder(directlyOrderRequest) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        callback(Resource.Success(it))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun orderFromCart(
        cartOrderRequest: CartOrderRequest,
        callback: (Resource<Map<String, Any>>) -> Unit,
    ) {
        orderRemoteDataSource.orderFromCart(cartOrderRequest) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        callback(Resource.Success(it))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun getAllOrders(callback: (Resource<List<Order>>) -> Unit) {
        orderRemoteDataSource.getAllOrder { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { listOrderResponses ->

                        val listOrders =
                            OrderMapper.listOrderResponseToListOrder(listOrderResponses)
                        callback(Resource.Success(listOrders))

                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }
}