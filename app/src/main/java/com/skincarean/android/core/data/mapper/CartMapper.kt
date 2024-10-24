package com.skincarean.android.core.data.mapper

import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.domain.model.cart.CartItem
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import java.util.stream.Collectors

object CartMapper {
    fun cartResponseToCart(input: CartResponse): Cart {

        val cartItemResponses = input.cartItems
        val cartItems = cartItemResponses?.stream()?.map { cartItemResponse ->
            val productResponse = cartItemResponse?.product
            val productImageItemResponses = productResponse?.productImage
            val productImageItems = productImageItemResponses?.stream()
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
                productImageItems,
                productResponse?.productDescription
            )
            CartItem(
                cartItemResponse?.total,
                product,
                cartItemResponse?.quantity,
                cartItemResponse?.id
            )
        }?.collect(Collectors.toList())
        return Cart(
            input.quantity,
            input.totalPrice,
            input.id,
            cartItems
        )
    }
}