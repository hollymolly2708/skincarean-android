package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.BrandRemoteDataSource

class BrandRepository private constructor(private val brandRemoteDataSource: BrandRemoteDataSource) {
    companion object {
        @Volatile
        var instance: BrandRepository? = null
        fun getInstance(brandRemoteDataSource: BrandRemoteDataSource): BrandRepository {
            if (instance == null) {
                synchronized(this) {
                    instance = BrandRepository(brandRemoteDataSource)
                }
            }
            return instance!!
        }
    }
    fun getAllBrandsByTopBrand(callback: (Any) -> Unit) {
        brandRemoteDataSource.getallBrandByTopBrands(callback)
    }
}