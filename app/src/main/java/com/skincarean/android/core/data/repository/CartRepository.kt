package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.CartRemoteDataSource
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.request.CartRequest

class CartRepository private constructor(private val cartRemoteDataSource: CartRemoteDataSource) {
    companion object {
        @Volatile
        var INSTANCE: CartRepository? = null
        fun getInstance(cartRemoteDataSource: CartRemoteDataSource): CartRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = CartRepository(cartRemoteDataSource)
                }
            }
            return INSTANCE!!
        }
    }

    fun addProductToCart(cartRequest: CartRequest, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        cartRemoteDataSource.addProductToCart(cartRequest, callback, callbackCartResponse)
    }

    fun getAllCarts(callback: (Any) -> Unit) {
        cartRemoteDataSource.getAllCarts(callback)
    }

    fun plusQuantity(cartId: Long, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        cartRemoteDataSource.plusQuantity(cartId, callback, callbackCartResponse)
    }

    fun minusQuantity(cartId: Long, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        cartRemoteDataSource.minusQuantity(cartId, callback, callbackCartResponse)
    }

    fun deleteCartItem(cartId: Long, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        cartRemoteDataSource.deleteCartItem(cartId, callback, callbackCartResponse)
    }

    fun deleteAllCartItem( callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        cartRemoteDataSource.deleteAllCartItem(callback, callbackCartResponse)
    }
}