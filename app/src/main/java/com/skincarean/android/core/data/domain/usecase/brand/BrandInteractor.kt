package com.skincarean.android.core.data.domain.usecase.brand

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.repository.BrandRepository

class BrandInteractor(private val brandRepository: BrandRepository) : BrandUseCase {
    override fun getAllBrands(callback: (Resource<List<Brand>>) -> Unit) {
        brandRepository.getAllBrands(callback)
    }
}