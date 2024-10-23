package com.skincarean.android.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.usecase.order.OrderUseCase
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val orderUseCase: OrderUseCase,
) : ViewModel() {
    private val _allOrders: MutableLiveData<List<Order>> = MutableLiveData()
    private val _allCompleteOrders: MutableLiveData<List<OrderResponse>> = MutableLiveData()
    private val _allPendingOrders: MutableLiveData<List<OrderResponse>> = MutableLiveData()
    private val _detailOrder: MutableLiveData<OrderResponse> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()

    val allOrders: LiveData<List<Order>> = _allOrders
    val detailOrder: LiveData<OrderResponse> = _detailOrder
    val allCompleteOrders: LiveData<List<OrderResponse>> = _allCompleteOrders
    val allPendingOrders: LiveData<List<OrderResponse>> = _allPendingOrders
    val message: LiveData<String> = _message

    fun getAllOrders() {
        orderRepository.getAllOrders { resource ->
            when (resource) {
                is Resource.Success -> {

                    resource.data?.let {
                        _allOrders.value = it
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

    fun detailOrder(orderId: String) {
        orderRepository.getDetailOrder(orderId) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _detailOrder.value = it as OrderResponse
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

    fun getAllCompleteOrders() {
        orderRepository.getAllCompleteOrders { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allCompleteOrders.value = it as List<OrderResponse>
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

    fun getAllPendingOrders() {
        orderRepository.getAllPendingOrders { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allPendingOrders.value = it as List<OrderResponse>
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