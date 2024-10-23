package com.skincarean.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.model.product.Product

import com.skincarean.android.core.data.domain.usecase.brand.BrandUseCase
import com.skincarean.android.core.data.repository.BrandRepository
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse

class HomeViewModel(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
    private val brandUseCase: BrandUseCase,
) : ViewModel() {
    private val _allBrandByTopBrand: MutableLiveData<List<Brand>> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _allPopularProduct: MutableLiveData<List<Product>> = MutableLiveData()
    private val _productByProductId: MutableLiveData<DetailProductResponse> = MutableLiveData()


    val allPopularProduct: LiveData<List<Product>> = _allPopularProduct
    val allBrandByTopBrand: LiveData<List<Brand>> = _allBrandByTopBrand
    val productByProductId: LiveData<DetailProductResponse> = _productByProductId
    val errorMessage: LiveData<String> = _message

    fun getAllBrandByTopBrand() {
        brandUseCase.getAllBrands { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _allBrandByTopBrand.value = it
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

    fun getAllPopularProduct() {
        productRepository.getAllPopularProduct { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _allPopularProduct.value = it
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