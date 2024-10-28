package com.skincarean.android.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.Review
import com.skincarean.android.core.data.domain.usecase.product.ProductUseCase
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class ProductViewModel(
    private val productUseCase: ProductUseCase,
) : ViewModel() {

    private val _allReviews: MutableLiveData<List<Review>> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _product: MutableLiveData<DetailProduct> = MutableLiveData()
    private val _listProduct: MutableLiveData<List<Product>> = MutableLiveData()
    private val _loading : MutableLiveData<Boolean> = MutableLiveData()


    val allReviews: LiveData<List<Review>> = _allReviews
    val product: LiveData<DetailProduct> = _product
    val message: LiveData<String> = _message
    val loading : LiveData<Boolean> = _loading
    val listProduct: LiveData<List<Product>> = _listProduct

    fun getAllReviews(productId: String) {
        productUseCase.getAllReviewsByProductId(productId) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _allReviews.value = it
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
                    _loading.value = false
                }

                is Resource.Error -> {
                    resource.message?.let {
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

    fun searchProduct(nameProduct: String, page: Int, size: Int) {
        productUseCase.searchProduct(nameProduct, page, size) { resource ->
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
}