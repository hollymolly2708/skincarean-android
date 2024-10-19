package com.skincarean.android.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.core.data.repository.CartRepository
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _allCart: MutableLiveData<CartResponse> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()


    val message: LiveData<String> = _message
    val allCart: LiveData<CartResponse> = _allCart


    fun addProductToCart(cartRequest: CartRequest) {
        cartRepository.addProductToCart(cartRequest, { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _message.value = it as String
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }

        }, { cartResponse ->
            when (cartResponse) {
                is WebResponse<*> -> {
                    cartResponse.data?.let {
                        _allCart.value = it as CartResponse
                    }
                }
            }
        })
    }

    fun getAllCarts() {
        cartRepository.getAllCarts { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allCart.value = it as CartResponse
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }
        }
    }

    fun plusQuantity(cartId: Long) {
        cartRepository.plusQuantity(cartId, { response ->
            when (response) {
                is WebResponse<*> -> {

                    response.data?.let {
                        _message.value = it as String
                    }

                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }
        }, { cartResponse ->
            when (cartResponse) {
                is WebResponse<*> -> {
                    cartResponse.data?.let {
                        _allCart.value = it as CartResponse
                    }
                }
            }
        })
    }

    fun minusQuantity(cartId: Long) {
        cartRepository.minusQuantity(cartId, { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _message.value = it as String
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }
        }, { cartResponse ->
            when (cartResponse) {
                is WebResponse<*> -> {
                    cartResponse.data?.let {
                        _allCart.value = it as CartResponse
                    }
                }
            }
        })
    }

    fun deleteCartItem(cartId: Long) {
        cartRepository.deleteCartItem(cartId, { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _message.value = it as String
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }
        }, { cartResponse ->
            when (cartResponse) {
                is WebResponse<*> -> {
                    cartResponse.data?.let {
                        _allCart.value = it as CartResponse
                    }
                }
            }
        })
    }

    fun deleteAllCartItem() {
        cartRepository.deleteAllCartItem({ response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _message.value = it as String
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }

        }, { cartResponse ->
            when (cartResponse) {
                is WebResponse<*> -> {
                    cartResponse.data?.let {
                        _allCart.value = it as CartResponse
                    }

                }
            }
        })
    }
}