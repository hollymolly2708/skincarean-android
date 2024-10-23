package com.skincarean.android.core.data.domain.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.Brand

interface IBrandRepository {
    fun getAllBrands(callback: (Resource<List<Brand>>) ->Unit)
}