package com.skincarean.android.core.data.mapper

import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.model.order.OrderItem
import com.skincarean.android.core.data.domain.model.order.OrderProduct
import com.skincarean.android.core.data.domain.model.order.OrderProductVariant
import com.skincarean.android.core.data.domain.model.payment.Payment
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.core.data.source.remote.response.order.OrderProductResponse
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

        val orderItemResponses = input.orderItems
        val orderItem = orderItemResponses?.stream()?.map { orderItem ->
            val orderProductResponse = orderItem?.product
            val orderProduct = OrderProduct(
                brandName = orderProductResponse?.brandName,
                productId = orderProductResponse?.productId,
                productName = orderProductResponse?.productName,
                categoryName = orderProductResponse?.categoryName,
                thumbnailImage = orderProductResponse?.thumbnailImage,
            )

            val orderProductVariantResponse = orderItem?.productVariant
            val orderProductVariant = OrderProductVariant(
                id = orderProductVariantResponse?.id,
                size = orderProductVariantResponse?.size,
                price = orderProductVariantResponse?.originalPrice,
                discount = orderProductVariantResponse?.discount,
                originalPrice = orderProductVariantResponse?.originalPrice,
                thumbnailImageVariant = orderProductVariantResponse?.thumbnailImageVariant
            )

            OrderItem(

                product = orderProduct,
                quantity = orderItem?.quantity,
                price = orderItem?.price,
                productVariant = orderProductVariant


            )

        }?.collect(Collectors.toList())
        val detailOrder = DetailOrder(
            input.shippingCost,
            input.orderId,
            input.orderStatus,
            input.finalPrice,
            input.description,
            input.shippingAddress,
            input.tax, payment, orderItem

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
                val productVariantResponse = it?.productVariant

                val product = OrderProduct(
                    brandName = productResponse?.brandName,
                    productId = productResponse?.productId,
                    productName = productResponse?.productName,
                    categoryName = productResponse?.categoryName,
                    thumbnailImage = productResponse?.thumbnailImage,
                )

                val productVariant = OrderProductVariant(
                    id = productVariantResponse?.id,
                    size = productVariantResponse?.size,
                    price = productVariantResponse?.price,
                    originalPrice = productVariantResponse?.originalPrice,
                    discount = productVariantResponse?.discount,
                    thumbnailImageVariant = productVariantResponse?.thumbnailImageVariant

                )
                OrderItem(
                    product = product,
                    quantity = it?.quantity,
                    price = it?.price,
                    productVariant = productVariant
                )
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