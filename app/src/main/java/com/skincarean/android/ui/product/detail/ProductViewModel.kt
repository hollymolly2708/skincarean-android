package com.skincarean.android.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.usecase.product.ProductUseCase
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val productUseCase: ProductUseCase,
) : ViewModel() {

    private val _allReviews: MutableLiveData<List<ReviewResponse>> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _product: MutableLiveData<DetailProduct> = MutableLiveData()
    private val _listProduct: MutableLiveData<List<Product>> = MutableLiveData()


    val allReviews: LiveData<List<ReviewResponse>> = _allReviews
    val product: LiveData<DetailProduct> = _product
    val message: LiveData<String> = _message
    val listProduct: LiveData<List<Product>> = _listProduct

    fun getAllReviews(productId: String) {
        productRepository.getAllReviewsByProductId(productId) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allReviews.value = it as List<ReviewResponse>
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


    fun getProductByProductId(productId: String) {

        productUseCase.getDetailProductById(productId) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _product.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    fun getAllProduct() {
        productUseCase.getAllProducts { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _listProduct.value = it
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

    fun searchProduct(nameProduct: String, page: Int, size: Int) {

    }
}