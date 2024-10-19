package com.skincarean.android.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class DetailProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _allReviews: MutableLiveData<List<ReviewResponse>> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _productByProductId: MutableLiveData<DetailProductResponse> = MutableLiveData()
    private val _allProducts: MutableLiveData<List<ProductResponse>> = MutableLiveData()


    val allReviews: LiveData<List<ReviewResponse>> = _allReviews
    val productByProductId: LiveData<DetailProductResponse> = _productByProductId
    val errorMessage: LiveData<String> = _errorMessage
    val allProducts: LiveData<List<ProductResponse>> = _allProducts

    fun getAllReviews(productId: String) {
        productRepository.getAllReviewsByProductId(productId) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allReviews.value = it as List<ReviewResponse>
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


    fun getProductByProductId(productId: String) {
        productRepository.getProductByProductId(productId = productId) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _productByProductId.value = it as DetailProductResponse
                    }
                    response.errors?.let {
                        _errorMessage.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error.let {
                        _errorMessage.value = it
                    }
                }
            }
        }
    }

    fun getAllProduct() {
        productRepository.getAllProducts { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allProducts.value = it as List<ProductResponse>
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
}