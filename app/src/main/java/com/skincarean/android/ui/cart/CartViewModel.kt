package com.skincarean.android.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.core.Resource
import com.skincarean.android.core.data.domain.model.cart.Cart
import com.skincarean.android.core.data.domain.usecase.cart.CartUseCase
import com.skincarean.android.core.data.source.remote.request.CartRequest

class CartViewModel(
    private val cartUseCase: CartUseCase,
) : ViewModel() {
    private val _cart: MutableLiveData<Cart> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _loading : MutableLiveData<Boolean>  = MutableLiveData()


    val message: LiveData<String> = _message
    val cart: LiveData<Cart> = _cart
    val loading : LiveData<Boolean> = _loading



    fun addProductToCart(cartRequest: CartRequest) {
        cartUseCase.addProductToCart(cartRequest, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _message.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }, { cartResource ->
            when (cartResource) {
                is Resource.Success -> {
                    cartResource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    cartResource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    fun getAllActiveCarts() {
        cartUseCase.getAllActiveCarts { resource ->

            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _cart.value = it
                    }

                    _loading.value = false
                }

                is Resource.Error -> {
                    resource.message.let {
                        _message.value = it
                    }

                    _loading.value = false
                }

                is Resource.Loading -> {
                    _loading.value = true
                }
            }
        }
    }

    fun getAllCarts() {
        cartUseCase.getAllCarts { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    fun plusQuantity(cartId: Long) {
        cartUseCase.plusQuantity(cartId, { resource ->
            when (resource) {
                is Resource.Success -> {

                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }, { cartResource ->
            when (cartResource) {
                is Resource.Success -> {
                    cartResource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    cartResource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    fun minusQuantity(cartId: Long) {
        cartUseCase.minusQuantity(cartId, { resource ->
            when (resource) {
                is Resource.Success -> {

                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }, { cartResource ->
            when (cartResource) {
                is Resource.Success -> {
                    cartResource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    cartResource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        })

    }

    fun deleteCartItem(cartId: Long) {
        cartUseCase.deleteCartItem(cartId, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _message.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }, { cartResource ->
            when (cartResource) {
                is Resource.Success -> {
                    cartResource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    cartResource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    fun deleteAllCartItem() {
        cartUseCase.deleteAllCartItem({ resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _message.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }, { cartResource ->
            when (cartResource) {
                is Resource.Success -> {
                    cartResource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    cartResource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    fun setActiveCart(cartId: Long){
        cartUseCase.setActiveCart(cartId, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {

                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }, { cartResource ->
            when (cartResource) {
                is Resource.Success -> {
                    cartResource.data?.let {
                        _cart.value = it
                    }
                }

                is Resource.Error -> {
                    cartResource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }
}