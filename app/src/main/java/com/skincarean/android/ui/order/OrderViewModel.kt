package com.skincarean.android.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.core.Resource
import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.core.data.domain.usecase.order.OrderUseCase

class OrderViewModel(
    private val orderUseCase: OrderUseCase,
) : ViewModel() {
    private val _allOrders: MutableLiveData<List<Order>> = MutableLiveData()
    private val _allCompleteOrders: MutableLiveData<List<Order>> = MutableLiveData()
    private val _allPendingOrders: MutableLiveData<List<Order>> = MutableLiveData()
    private val _allCancelOrders: MutableLiveData<List<Order>> = MutableLiveData()
    private val _detailOrder: MutableLiveData<DetailOrder> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _isLoading : MutableLiveData<Boolean> = MutableLiveData()

    val isLoading : LiveData<Boolean> = _isLoading
    val allOrders: LiveData<List<Order>> = _allOrders
    val detailOrder: LiveData<DetailOrder> = _detailOrder
    val allCancelOrders: LiveData<List<Order>> = _allCancelOrders
    val allCompleteOrders: LiveData<List<Order>> = _allCompleteOrders
    val allPendingOrders: LiveData<List<Order>> = _allPendingOrders
    val message: LiveData<String> = _message

    fun getAllOrders() {
        orderUseCase.getAllOrders { resource ->
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
        orderUseCase.getDetailOrder(orderId) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _detailOrder.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }
    }

    fun getAllCompleteOrders() {
        orderUseCase.getAllCompleteOrder { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _allCompleteOrders.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }
    }

    fun getAllPendingOrders() {
        orderUseCase.getAllPendingOrder { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _allPendingOrders.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }

    }

    fun getAllCancelOrders() {
        orderUseCase.getAllCancelOrder { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _allCancelOrders.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }
    }
}