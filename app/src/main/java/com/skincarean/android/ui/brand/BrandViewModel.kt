package com.skincarean.android.ui.brand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.DetailBrand
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.usecase.brand.BrandUseCase
import com.skincarean.android.core.data.domain.usecase.product.ProductUseCase

class BrandViewModel(
    private val brandUseCase: BrandUseCase,
) : ViewModel() {
    private val _detailBrand: MutableLiveData<DetailBrand> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()
    private val _allProductsByBrand: MutableLiveData<List<Product>> = MutableLiveData()

    var selectedBrandId: Long? = null
    var brandTitle: String? = null
    var brandDescription : String? = null

    val allProductsByBrand: LiveData<List<Product>> = _allProductsByBrand
    val message: LiveData<String> = _message
    val detailBrand: LiveData<DetailBrand> = _detailBrand

    fun getDetailBrandByBrand(brandId: Long) {
        brandUseCase.getDetailBrandByBrandId(brandId) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _detailBrand.value = it
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

    fun getAllProductsByBrand(brandId: Long) {
        brandUseCase.getAllProductsByBrand(brandId) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _allProductsByBrand.value = it
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