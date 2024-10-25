package com.skincarean.android.core.data.domain.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.model.brand.DetailBrand
import com.skincarean.android.core.data.domain.model.product.Product

interface IBrandRepository {
    fun getAllBrands(callback: (Resource<List<Brand>>) ->Unit)
    fun detailBrandByBrandId(brandId : Long, callback: (Resource<DetailBrand>) -> Unit)
    fun getAllProductsByBrand(brandId: Long, callback: (Resource<List<Product>>) -> Unit)
}