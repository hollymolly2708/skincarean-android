package com.skincarean.android.core.data.domain.usecase.brand


import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.model.brand.DetailBrand
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.repository.BrandRepository
import com.skincarean.core.Resource

class BrandInteractor(private val brandRepository: BrandRepository) : BrandUseCase {
    override fun getAllBrands(callback: (Resource<List<Brand>>) -> Unit) {
        brandRepository.getAllBrands(callback)
    }

    override fun getDetailBrandByBrandId(brandId: Long, callback: (Resource<DetailBrand>) -> Unit) {
        brandRepository.detailBrandByBrandId(brandId, callback)
    }

    override fun getAllProductsByBrand(brandId: Long, callback: (Resource<List<Product>>) -> Unit) {
        brandRepository.getAllProductsByBrand(brandId, callback)
    }

}