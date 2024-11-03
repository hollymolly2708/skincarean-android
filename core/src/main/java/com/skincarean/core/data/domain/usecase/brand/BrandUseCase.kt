package com.skincarean.android.core.data.domain.usecase.brand


import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.model.brand.DetailBrand
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.core.Resource

interface BrandUseCase {
    fun getAllBrands(callback: (Resource<List<Brand>>) -> Unit)
    fun getDetailBrandByBrandId(brandId : Long, callback: (Resource<DetailBrand>) -> Unit)
    fun getAllProductsByBrand(brandId: Long, callback: (Resource<List<Product>>) -> Unit)

}