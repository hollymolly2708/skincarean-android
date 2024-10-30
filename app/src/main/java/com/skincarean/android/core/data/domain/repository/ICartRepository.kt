package com.skincarean.android.core.data.domain.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.source.remote.request.CartRequest


interface ICartRepository {
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

    fun getAllActiveCarts(callbackCart: (Resource<Cart>) -> Unit)

    fun setActiveCart(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    )

}