package com.skincarean.android.core.data.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.model.order.OrderItem
import com.skincarean.android.core.data.domain.model.payment.Payment
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.domain.repository.IOrderRepository
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.OrderRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import java.util.stream.Collectors

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


//    fun cartOrder(
//        cartOrderRequest: CartOrderRequest,
//        callback: (Resource<Map<String, Any>>) -> Unit,
//    ) {
//        orderRemoteDataSource.orderFromCart(cartOrderRequest, callback)
//    }

//    fun getAllOrders(callback: (Any) -> Unit) {
//        orderRemoteDataSource.getAllOrder(callback)
//    }

    fun getDetailOrder(orderId: String, callback: (Any) -> Unit) {
        orderRemoteDataSource.getDetailOrder(orderId, callback)
    }

    fun getAllCompleteOrders(callback: (Any) -> Unit) {

        orderRemoteDataSource.getAllCompleteOrders(callback)
    }

    fun getAllPendingOrders(callback: (Any) -> Unit) {
        orderRemoteDataSource.getAllPendingOrders(callback)
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
                        listOrderResponses.stream().map { orderResponse ->

                            val paymentResponse = orderResponse.payment
                            val payment = Payment(
                                paymentResponse?.paymentCode,
                                paymentResponse?.paidDate,
                                paymentResponse?.totalPaid,
                                paymentResponse?.paymentMethodId,
                                paymentResponse?.paymentStatus
                            )

                            val orderItemResponse = orderResponse.orderItems

                            val orderItems = orderItemResponse?.stream()?.map {
                                val productResponse = it?.product
                                val productImageResponse = productResponse?.productImage
                                val productImage = productImageResponse?.stream()
                                    ?.map { productImageItemResponse ->
                                        ProductImageItem(
                                            productImageItemResponse?.imageUrl,
                                            productImageItemResponse?.id
                                        )

                                    }?.collect(Collectors.toList())

                                val product = Product(
                                    productResponse?.brandName,
                                    productResponse?.productId,
                                    productResponse?.originalPrice,
                                    productResponse?.discount,
                                    productResponse?.productName,
                                    productResponse?.stok,
                                    productResponse?.isPromo,
                                    productResponse?.categoryName,
                                    productResponse?.bpomCode,
                                    productResponse?.size,
                                    productResponse?.price,
                                    productResponse?.isPopularProduct,
                                    productResponse?.thumbnailImage,
                                    productImage,
                                    productResponse?.productDescription
                                )
                                OrderItem(it?.createdAt, it?.expiredAt, product)
                            }?.collect(Collectors.toList())

                            Order(
                                orderResponse.shippingCost,
                                orderResponse.orderId,
                                orderResponse.orderId,
                                orderResponse.finalPrice,
                                orderResponse.description,
                                orderResponse.shippingAddress,
                                orderResponse.tax,
                                payment,
                                orderItems
                            )
                        }
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