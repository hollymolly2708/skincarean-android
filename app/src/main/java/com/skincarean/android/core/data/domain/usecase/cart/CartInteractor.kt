package com.skincarean.android.core.data.domain.usecase.cart

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.repository.CartRepository
import com.skincarean.android.core.data.source.remote.request.CartRequest

class CartInteractor(private val cartRepository: CartRepository) : CartUseCase {
    override fun getAllCarts(callback: (Resource<Cart>) -> Unit) {
        cartRepository.getAllCarts(callback)
    }

    override fun addProductToCart(
        cartRequest: CartRequest,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRepository.addProductToCart(cartRequest, callback, callbackCart)
    }

    override fun plusQuantity(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRepository.plusQuantity(cartId, callback, callbackCart)
    }

    override fun minusQuantity(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRepository.minusQuantity(cartId, callback, callbackCart)
    }

    override fun deleteCartItem(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRepository.deleteCartItem(cartId, callback, callbackCart)
    }

    override fun deleteAllCartItem(
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRepository.deleteAllCartItem(callback, callbackCart)
    }
}