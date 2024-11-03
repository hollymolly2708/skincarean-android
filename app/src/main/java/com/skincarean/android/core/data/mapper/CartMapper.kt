package com.skincarean.android.core.data.mapper

import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.domain.model.cart.CartItem
import com.skincarean.android.core.data.domain.model.cart.CartProduct
import com.skincarean.android.core.data.domain.model.cart.CartProductVariant
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.domain.model.product.ProductVariant
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import java.util.stream.Collectors

object CartMapper {
    fun cartResponseToCart(input: CartResponse): Cart {

        val cartItemResponses = input.cartItems
        val cartItems = cartItemResponses?.stream()?.map { cartItemResponse ->
            val productResponse = cartItemResponse?.product
            val product = CartProduct(
                brandName = productResponse?.brandName,
                productId = productResponse?.productId,
                productName = productResponse?.productName,
                categoryName = productResponse?.categoryName,
                thumbnailImage = productResponse?.thumbnailImage,
            )

            val productVariantResponse = cartItemResponse?.productVariant
            val cartProductVariant = CartProductVariant(
                id = productVariantResponse?.id,
                size = productVariantResponse?.size,
                price = productVariantResponse?.price,
                originalPrice = productVariantResponse?.originalPrice,
                discount = productVariantResponse?.discount,
                thumbnailVariantImage = productVariantResponse?.thumbnailVariantImage

            )
            CartItem(
                total = cartItemResponse?.total,
                product = product,
                quantity = cartItemResponse?.quantity,
                id = cartItemResponse?.id,
                isActive = cartItemResponse?.isActive,
                productVariant = cartProductVariant
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