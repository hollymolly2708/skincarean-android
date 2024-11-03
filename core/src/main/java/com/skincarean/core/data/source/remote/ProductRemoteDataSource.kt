package com.skincarean.android.core.data.source.remote

import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponseBySingleVariant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        var instance: ProductRemoteDataSource? = null
        fun getInstance(apiService: ApiService): ProductRemoteDataSource {
            if (instance == null) {
                synchronized(this) {
                    instance = ProductRemoteDataSource(apiService)
                }
            }
            return instance!!
        }
    }

    fun getAllPopularProducts(callback: (ApiResponse<WebResponse<List<ProductResponse>>>) -> Unit) {
        apiService.getAllTopProducts()
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
                        callback(ApiResponse.Error(errorBody ?: "Unknown Error"))

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

    fun getDetailProductById(
        productId: String,
        callback: (ApiResponse<WebResponse<DetailProductResponse>>) -> Unit,
    ) {
        apiService.getProductByProductId(productId)
            .enqueue(object : Callback<WebResponse<DetailProductResponse>> {
                override fun onResponse(
                    call: Call<WebResponse<DetailProductResponse>>,
                    response: Response<WebResponse<DetailProductResponse>>,
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
                    call: Call<WebResponse<DetailProductResponse>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }



    fun getDetailProductByProductIdAndVariantId(
        productId: String,
        variantId :Long,
        callback: (ApiResponse<WebResponse<DetailProductResponseBySingleVariant>>) -> Unit,
    ) {
        apiService.getDetailProductByProductIdAndVariantId(productId,variantId)
            .enqueue(object : Callback<WebResponse<DetailProductResponseBySingleVariant>> {
                override fun onResponse(
                    call: Call<WebResponse<DetailProductResponseBySingleVariant>>,
                    response: Response<WebResponse<DetailProductResponseBySingleVariant>>,
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
                    call: Call<WebResponse<DetailProductResponseBySingleVariant>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }





    fun getallReviewByProductId(
        productId: String,
        callback: (ApiResponse<WebResponse<List<ReviewResponse>>>) -> Unit,
    ) {
        apiService.getAllReviewByProductId(productId)
            .enqueue(object : Callback<WebResponse<List<ReviewResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<ReviewResponse>>>,
                    response: Response<WebResponse<List<ReviewResponse>>>,
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
                    call: Call<WebResponse<List<ReviewResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }


    fun getAllProducts(callback: (ApiResponse<WebResponse<List<ProductResponse>>>) -> Unit) {
        apiService.getAllProducts().enqueue(object : Callback<WebResponse<List<ProductResponse>>> {
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

            override fun onFailure(call: Call<WebResponse<List<ProductResponse>>>, t: Throwable) {
                t.printStackTrace()

                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun searchProduct(
        nameProduct: String,
        page: Int,
        size: Int,
        callback: (ApiResponse<WebResponse<List<ProductResponse>>>) -> Unit,
    ) {
        apiService.searchProducts(nameProduct, page, size)
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