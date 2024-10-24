package com.skincarean.android.core.data.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.domain.repository.ICartRepository
import com.skincarean.android.core.data.mapper.CartMapper
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.CartRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.CartRequest

class CartRepository private constructor(private val cartRemoteDataSource: CartRemoteDataSource) :
    ICartRepository {
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


    override fun getAllCarts(callback: (Resource<Cart>) -> Unit) {
        cartRemoteDataSource.getAllCarts { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { cartResponse ->
                        val cart = CartMapper.cartResponseToCart(cartResponse)
                        callback(Resource.Success(cart))
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

    override fun addProductToCart(
        cartRequest: CartRequest,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRemoteDataSource.addProductToCart(cartRequest, { apiResponse ->
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
                    callback(Resource.Error("No data available"))
                }
            }
        }, { cartApiResponse ->
            when (cartApiResponse) {
                is ApiResponse.Success -> {
                    cartApiResponse.data.data?.let { cartResponse ->
                        val cart = CartMapper.cartResponseToCart(cartResponse)
                        callbackCart(Resource.Success(cart))
                    }
                }

                is ApiResponse.Error -> {
                    cartApiResponse.errorMessage?.let {
                        callbackCart(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callbackCart(Resource.Error("No data available"))
                }
            }
        })
    }

    override fun plusQuantity(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRemoteDataSource.plusQuantity(cartId, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> apiResponse.data.data?.let {
                    callback(Resource.Success(it))
                }

                is ApiResponse.Error -> apiResponse.errorMessage?.let {
                    callback(Resource.Error(it))
                }

                is ApiResponse.Empty -> callback(Resource.Error("No data available"))
            }
        }, { cartApiResponse ->
            when (cartApiResponse) {
                is ApiResponse.Success -> {
                    cartApiResponse.data.data?.let { cartResponse ->
                        val cart = CartMapper.cartResponseToCart(cartResponse)
                        callbackCart(Resource.Success(cart))
                    }
                }

                is ApiResponse.Error -> {
                    cartApiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        })
    }

    override fun minusQuantity(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRemoteDataSource.minusQuantity(cartId, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> apiResponse.data.data?.let {
                    callback(Resource.Success(it))
                }

                is ApiResponse.Error -> apiResponse.errorMessage?.let {
                    callback(Resource.Error(it))
                }

                is ApiResponse.Empty -> callback(Resource.Error("No data available"))
            }
        }, { cartApiResponse ->
            when (cartApiResponse) {
                is ApiResponse.Success -> {
                    cartApiResponse.data.data?.let { cartResponse ->
                        val cart = CartMapper.cartResponseToCart(cartResponse)
                        callbackCart(Resource.Success(cart))
                    }
                }

                is ApiResponse.Error -> {
                    cartApiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        })
    }

    override fun deleteCartItem(
        cartId: Long,
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRemoteDataSource.deleteCartItem(cartId, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> apiResponse.data.data?.let {
                    callback(Resource.Success(it))
                }

                is ApiResponse.Error -> apiResponse.errorMessage?.let {
                    callback(Resource.Error(it))
                }

                is ApiResponse.Empty -> callback(Resource.Error("No data available"))
            }
        }, { cartApiResponse ->
            when (cartApiResponse) {
                is ApiResponse.Success -> {
                    cartApiResponse.data.data?.let { cartResponse ->
                        val cart = CartMapper.cartResponseToCart(cartResponse)
                        callbackCart(Resource.Success(cart))
                    }
                }

                is ApiResponse.Error -> {
                    cartApiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        })
    }

    override fun deleteAllCartItem(
        callback: (Resource<String>) -> Unit,
        callbackCart: (Resource<Cart>) -> Unit,
    ) {
        cartRemoteDataSource.deleteAllCartItem({ apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> apiResponse.data.data?.let {
                    callback(Resource.Success(it))
                }

                is ApiResponse.Error -> apiResponse.errorMessage?.let {
                    callback(Resource.Error(it))
                }

                is ApiResponse.Empty -> callback(Resource.Error("No data available"))
            }
        }, { cartApiResponse ->
            when (cartApiResponse) {
                is ApiResponse.Success -> {
                    cartApiResponse.data.data?.let { cartResponse ->
                        val cart = CartMapper.cartResponseToCart(cartResponse)
                        callbackCart(Resource.Success(cart))
                    }
                }

                is ApiResponse.Error -> {
                    cartApiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        })
    }
}