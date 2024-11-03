package com.skincarean.android.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.android.core.data.domain.usecase.order.OrderUseCase
import com.skincarean.android.core.data.domain.usecase.payment_method.PaymentMethodUseCase
import com.skincarean.android.core.data.domain.usecase.product.ProductUseCase
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class CheckoutViewModel(
    private val productUseCase: ProductUseCase,
    private val paymentMethodUseCase: PaymentMethodUseCase,
    private val orderUseCase: OrderUseCase,
) :
    ViewModel() {
    private val _listPaymentMethods: MutableLiveData<List<PaymentMethod>> = MutableLiveData()
    private val _detailProduct: MutableLiveData<DetailProduct> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _orderId: MutableLiveData<Map<String, Any>> = MutableLiveData()


    var _selectedPaymentMethodId: Int? = null
    var _selectedDescription: String? = null


    val orderId: LiveData<Map<String, Any>> = _orderId
    val message: LiveData<String> = _message
    val detailProduct: LiveData<DetailProduct> = _detailProduct
    val listPaymentMethods: LiveData<List<PaymentMethod>> = _listPaymentMethods

    fun getAllPaymentMethods() {
        paymentMethodUseCase.getAllPaymentMethods { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _listPaymentMethods.value = it
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

    fun getDetailProductByProductIdAndVariantId(productId: String, variantId: Long) {
        productUseCase.getDetailProductByProductIdAndVariantId(productId, variantId) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _detailProduct.value = it
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

    fun directlyOrder(directlyOrderRequest: DirectlyOrderRequest) {
        orderUseCase.directlyOrder(directlyOrderRequest) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _orderId.value = it
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

    fun cartOrder(cartOrderRequest: CartOrderRequest) {
        orderUseCase.orderFromCart(cartOrderRequest) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _orderId.value = it
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
}