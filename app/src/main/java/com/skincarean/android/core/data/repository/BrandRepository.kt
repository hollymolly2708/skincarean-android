package com.skincarean.android.core.data.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.repository.IBrandRepository
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.BrandRemoteDataSource
import java.util.stream.Collectors

class BrandRepository private constructor(private val brandRemoteDataSource: BrandRemoteDataSource) :
    IBrandRepository {
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

    override fun getAllBrands(callback: (Resource<List<Brand>>) -> Unit) {
        brandRemoteDataSource.getallBrandByTopBrands { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { brandResponses ->
                        val brands = brandResponses.stream().map {
                            Brand(
                                it.brandPoster,
                                it.address,
                                it.lastUpdatedAt,
                                it.description,
                                it.contactEmailUrl,
                                it.createdAt,
                                it.facebookUrl,
                                it.isTopBrand,
                                it.name,
                                it.instagramUrl,
                                it.id,
                                it.brandLogo
                            )
                        }.collect(Collectors.toList())

                        callback(Resource.Success(brands))

                    }

                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

}