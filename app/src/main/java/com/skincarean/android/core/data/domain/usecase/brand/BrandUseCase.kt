package com.skincarean.android.core.data.domain.usecase.brand

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.Brand

interface BrandUseCase {
    fun getAllBrands(callback: (Resource<List<Brand>>) -> Unit)
}