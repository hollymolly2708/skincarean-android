package com.skincarean.android.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.repository.PaymentMethodRepository
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class CheckoutViewModel(
    private val productRepository: ProductRepository,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val orderRepository: OrderRepository,
) :
    ViewModel() {
    private val _allPaymentMethods: MutableLiveData<List<PaymentMethodResponse>> = MutableLiveData()
    private val _detailProduct: MutableLiveData<DetailProductResponse> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    var _selectedPaymentMethodId: Int? = null
    var _selectedDescription: String? = null


    val errorMessage: LiveData<String> = _errorMessage
    val message: LiveData<String> = _message
    val detailProduct: LiveData<DetailProductResponse> = _detailProduct
    val allPaymentMethods: LiveData<List<PaymentMethodResponse>> = _allPaymentMethods

    fun getAllPaymentMethods() {
        paymentMethodRepository.getAllPaymentMethods { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allPaymentMethods.value = it as List<PaymentMethodResponse>
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

    fun directlyOrderRequest(directlyOrderRequest: DirectlyOrderRequest) {
        orderRepository.directlyOrder(directlyOrderRequest) { response ->
            when (response) {
                is WebResponse<*> -> {
                    if (response.data != null) {
                        response.data.let {
                            _message.value = it as String
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
}