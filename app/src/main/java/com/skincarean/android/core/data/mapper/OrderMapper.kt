package com.skincarean.android.core.data.mapper

import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.model.order.OrderItem
import com.skincarean.android.core.data.domain.model.payment.Payment
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import java.util.stream.Collectors

object OrderMapper {
    fun orderResponseToDetailOrder(input: OrderResponse): DetailOrder {

        val paymentResponse = input.payment
        val payment = Payment(
            paymentResponse?.paymentCode,
            paymentResponse?.paidDate,
            paymentResponse?.totalPaid,
            paymentResponse?.paymentMethodId,
            paymentResponse?.paymentStatus,
            paymentResponse?.paymentMethodName
        )

        val orderItemsResponse = input.orderItems
        val orderItems = orderItemsResponse?.stream()?.map { orderItem ->
            val productResponse = orderItem?.product
            val productImageItemResponse = productResponse?.productImage
            val productImageItem = productImageItemResponse?.stream()
                ?.map { ProductImageItem(it?.imageUrl, it?.id) }
                ?.collect(Collectors.toList())
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
                productImageItem,
                productResponse?.productDescription
            )
            OrderItem(
                orderItem?.createdAt,
                orderItem?.expiredAt,
                product,
                orderItem?.quantity,
                orderItem?.price
            )
        }?.collect(Collectors.toList())
        val detailOrder = DetailOrder(
            input.shippingCost,
            input.orderId,
            input.orderStatus,
            input.finalPrice,
            input.description,
            input.shippingAddress,
            input.tax, payment, orderItems

        )
        return detailOrder
    }

    fun listOrderResponseToListOrder(input: List<OrderResponse>): List<Order> {
        val listOrders = input.stream().map { orderResponse ->

            val paymentResponse = orderResponse.payment
            val payment = Payment(
                paymentResponse?.paymentCode,
                paymentResponse?.paidDate,
                paymentResponse?.totalPaid,
                paymentResponse?.paymentMethodId,
                paymentResponse?.paymentStatus,
                paymentResponse?.paymentMethodName
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
                OrderItem(it?.createdAt, it?.expiredAt, product, it?.quantity, it?.price)
            }?.collect(Collectors.toList())

            Order(
                orderResponse.shippingCost,
                orderResponse.orderId,
                orderResponse.orderStatus,
                orderResponse.finalPrice,
                orderResponse.description,
                orderResponse.shippingAddress,
                orderResponse.tax,
                payment,
                orderItems
            )
        }.collect(Collectors.toList())

        return listOrders
    }
}