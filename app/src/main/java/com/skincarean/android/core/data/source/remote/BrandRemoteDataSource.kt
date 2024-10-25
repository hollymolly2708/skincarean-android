package com.skincarean.android.core.data.source.remote

import com.google.android.gms.common.api.Api
import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.brand.DetailBrandResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrandRemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        var instance: BrandRemoteDataSource? = null
        fun getInstance(apiService: ApiService): BrandRemoteDataSource {
            if (instance == null) {
                synchronized(this) {
                    instance = BrandRemoteDataSource(apiService)
                }
            }
            return instance!!
        }
    }

    fun getallBrandByTopBrands(callback: (ApiResponse<WebResponse<List<BrandResponse>>>) -> Unit) {

        apiService.getBrandsByTopBrand()
            .enqueue(object : Callback<WebResponse<List<BrandResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<BrandResponse>>>,
                    response: Response<WebResponse<List<BrandResponse>>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(ApiResponse.Success(body))
                        } else {
                            callback(ApiResponse.Error("Response body is null"))
                        }

                    } else {
                        val errorBody = response.errorBody()?.string()
                        callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                    }
                }

                override fun onFailure(call: Call<WebResponse<List<BrandResponse>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })

    }

    fun getDetailBrandByBrandId(
        brandId: Long,
        callback: (ApiResponse<WebResponse<DetailBrandResponse>>) -> Unit,
    ) {
        apiService.getDetailBrandByBrandId(brandId)
            .enqueue(object : Callback<WebResponse<DetailBrandResponse>> {
                override fun onResponse(
                    call: Call<WebResponse<DetailBrandResponse>>,
                    response: Response<WebResponse<DetailBrandResponse>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(ApiResponse.Success(body))
                        } else {
                            callback(ApiResponse.Error("Response body is null"))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                    }
                }

                override fun onFailure(call: Call<WebResponse<DetailBrandResponse>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }


            })
    }

    fun getAllProductsByBrand(
        brandId: Long,
        callback: (ApiResponse<WebResponse<List<ProductResponse>>>) -> Unit,
    ) {
        apiService.getAllProductsByBrand(brandId)
            .enqueue(object : Callback<WebResponse<List<ProductResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<ProductResponse>>>,
                    response: Response<WebResponse<List<ProductResponse>>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(ApiResponse.Success(body))
                        } else {
                            callback(ApiResponse.Error("Response body is null"))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                    }
                }

                override fun onFailure(
                    call: Call<WebResponse<List<ProductResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }
}

