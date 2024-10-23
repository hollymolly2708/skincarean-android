package com.skincarean.android.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.PaymentMethod
import com.skincarean.android.core.data.domain.usecase.PaymentMethodUseCase
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.repository.PaymentMethodRepository
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class CheckoutViewModel(
    private val productRepository: ProductRepository,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val paymentMethodUseCase: PaymentMethodUseCase,
    private val orderRepository: OrderRepository,
) :
    ViewModel() {
    private val _allPaymentMethods: MutableLiveData<List<PaymentMethod>> = MutableLiveData()
    private val _detailProduct: MutableLiveData<DetailProductResponse> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _orderId: MutableLiveData<Map<String, Any>> = MutableLiveData()
    var _selectedPaymentMethodId: Int? = null
    var _selectedDescription: String? = null


    val errorMessage: LiveData<String> = _errorMessage

    val orderId: LiveData<Map<String, Any>> = _orderId
    val message: LiveData<String> = _message
    val detailProduct: LiveData<DetailProductResponse> = _detailProduct
    val allPaymentMethods: LiveData<List<PaymentMethod>> = _allPaymentMethods

    fun getAllPaymentMethods() {
        paymentMethodUseCase.getAllPaymentMethods { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _allPaymentMethods.value = it
                    }
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }
            }
        }
    }

    fun getDetailProduct(productId: String) {
        productRepository.getProductByProductId(productId) { response ->

            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _detailProduct.value = it as DetailProductResponse
                    }

                    response.errors?.let {
                        _errorMessage.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _errorMessage.value = it
                    }
                }
            }

        }
    }

    fun directlyOrder(directlyOrderRequest: DirectlyOrderRequest) {
        orderRepository.directlyOrder(directlyOrderRequest) { response ->
            when (response) {
                is WebResponse<*> -> {
                    if (response.data != null) {
                        response.data.let {
                            _orderId.value = it as Map<String, Any>
                        }
                    } else {
                        response.errors.let {
                            _message.value = it as String
                        }
                    }

                }
            }
        }
    }

    fun cartOrder(cartOrderRequest: CartOrderRequest) {
        orderRepository.cartOrder(cartOrderRequest) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _orderId.value = it as Map<String, Any>
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
}