package com.skincarean.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.core.data.repository.BrandRepository
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class HomeViewModel(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _allBrandByTopBrand: MutableLiveData<List<BrandResponse>> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _allPopularProduct: MutableLiveData<List<ProductResponse>> = MutableLiveData()
    private val _productByProductId: MutableLiveData<DetailProductResponse> = MutableLiveData()


    val allPopularProduct: LiveData<List<ProductResponse>> = _allPopularProduct
    val allBrandByTopBrand: LiveData<List<BrandResponse>> = _allBrandByTopBrand
    val productByProductId: LiveData<DetailProductResponse> = _productByProductId
    val errorMessage: LiveData<String> = _errorMessage

    fun getAllBrandByTopBrand() {
        brandRepository.getAllBrandsByTopBrand { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {

                        _allBrandByTopBrand.value = it as List<BrandResponse>
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

    fun getAllPopularProduct() {
        productRepository.getAllPopularProduct { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _allPopularProduct.value = it as List<ProductResponse>
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