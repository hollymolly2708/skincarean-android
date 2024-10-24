package com.skincarean.android.core.data.domain.usecase.cart

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse

interface CartUseCase {
    fun getAllCarts(callback: (Resource<Cart>) -> Unit)
    fun addProductToCart(
        cartRequest: CartRequest,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    )
    fun plusQuantity(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    )

    fun minusQuantity(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    )

    fun deleteCartItem(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    )

    fun deleteAllCartItem(
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    )
}