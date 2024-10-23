package com.skincarean.android.core.data.source.remote

import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
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

    fun getAllPopularProducts(callback: (Any) -> Unit) {
        apiService.getAllTopProducts()
            .enqueue(object : Callback<WebResponse<List<ProductResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<ProductResponse>>>,
                    response: Response<WebResponse<List<ProductResponse>>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(
                                WebResponse(
                                    body.data,
                                    body.paging,
                                    body.errors,
                                    body.isSuccess
                                )
                            )
                        } else {
                            callback(WebResponse(null, null, "Response body is null", false))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, WebResponse::class.java)
                            val errorResponseFromServer =
                                gson.fromJson(errorBody, ErrorResponse::class.java)
                            callback(errorResponse)
                            callback(errorResponseFromServer)
                        } else {
                            callback(WebResponse(null, null, "Response Error Body is null", false))
                        }

                    }
                }

                override fun onFailure(
                    call: Call<WebResponse<List<ProductResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(WebResponse(null, null, t.message, false))
                }

            })
    }

    fun getProductByProductId(productId: String, callback: (Any) -> Unit) {
        apiService.getProductByProductId(productId)
            .enqueue(object : Callback<WebResponse<DetailProductResponse>> {
                override fun onResponse(
                    call: Call<WebResponse<DetailProductResponse>>,
                    response: Response<WebResponse<DetailProductResponse>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(body)
                        } else {
                            WebResponse(null, null, "Response body is null", false)
                        }

                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, WebResponse::class.java)
                            val errorResponseFromServer =
                                gson.fromJson(errorBody, ErrorResponse::class.java)
                            callback(errorResponse)
                            callback(errorResponseFromServer)
                        } else {
                            callback(WebResponse(null, null, "Response error body is null", false))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<WebResponse<DetailProductResponse>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(WebResponse(null, null, t.message, false))
                }

            })
    }

    fun getallReviewByProductId(productId: String, callback: (Any) -> Unit) {
        apiService.getAllReviewByProductId(productId)
            .enqueue(object : Callback<WebResponse<List<ReviewResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<ReviewResponse>>>,
                    response: Response<WebResponse<List<ReviewResponse>>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(body)
                        } else {
                            WebResponse(null, null, "Response body is null", false)
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, WebResponse::class.java)
                            val errorResponseFromServer =
                                gson.fromJson(errorBody, ErrorResponse::class.java)
                            callback(errorResponse)
                            callback(errorResponseFromServer)
                        } else {
                            callback(WebResponse(null, null, "Response Error body is null", false))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<WebResponse<List<ReviewResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(WebResponse(null, null, t.message, false))
                }

            })
    }


    fun getAllProducts(callback: (Any) -> Unit) {
        apiService.getAllProducts().enqueue(object : Callback<WebResponse<List<ProductResponse>>> {
            override fun onResponse(
                call: Call<WebResponse<List<ProductResponse>>>,
                response: Response<WebResponse<List<ProductResponse>>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(body)
                    } else {
                        callback(WebResponse(null, null, "Response body is null", false))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, WebResponse::class.java)
                        val errorResponseFromServer =
                            gson.fromJson(errorBody, ErrorResponse::class.java)
                        callback(errorResponse)
                        callback(errorResponseFromServer)
                    } else {
                        callback(WebResponse(null, null, "Response error body is null", false))
                    }
                }
            }

            override fun onFailure(call: Call<WebResponse<List<ProductResponse>>>, t: Throwable) {
                t.printStackTrace()

                callback(WebResponse(null, null, t.message, false))
            }

        })
    }

    fun searchProduct(nameProduct: String, page: Int, size: Int, callback: (Any) -> Unit) {
        apiService.searchProducts(nameProduct, page, size)
            .enqueue(object : Callback<WebResponse<List<ProductResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<ProductResponse>>>,
                    response: Response<WebResponse<List<ProductResponse>>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {

                            callback(body)
                        } else {
                            callback(WebResponse(null, null, "Response body is null", false))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, WebResponse::class.java)
                            val errorResponseFromServer =
                                gson.fromJson(errorBody, ErrorResponse::class.java)
                            callback(errorResponse)
                            callback(errorResponseFromServer)
                        } else {
                            callback(WebResponse(null, null, "Response error body is null", false))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<WebResponse<List<ProductResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(WebResponse(null, null, t.message, false))
                }

            })
    }
}