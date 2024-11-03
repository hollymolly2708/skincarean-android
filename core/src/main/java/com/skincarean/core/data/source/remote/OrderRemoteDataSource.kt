package com.skincarean.android.core.data.source.remote

import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        var INSTANCE: OrderRemoteDataSource? = null
        fun getInstance(apiService: ApiService): OrderRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = OrderRemoteDataSource(apiService)
                }
            }
            return INSTANCE!!
        }
    }

    fun directlyOrder(
        directlyOrderRequest: DirectlyOrderRequest,
        callback: (ApiResponse<WebResponse<Map<String, Any>>>) -> Unit,
    ) {
        apiService.directlyOrder(directlyOrderRequest)
            .enqueue(object : Callback<WebResponse<Map<String, Any>>> {
                override fun onResponse(
                    call: Call<WebResponse<Map<String, Any>>>,
                    response: Response<WebResponse<Map<String, Any>>>,
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

                override fun onFailure(call: Call<WebResponse<Map<String, Any>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }


    fun orderFromCart(
        cartOrderRequest: CartOrderRequest,
        callback: (ApiResponse<WebResponse<Map<String, Any>>>) -> Unit,
    ) {
        apiService.cartOrder(cartOrderRequest)
            .enqueue(object : Callback<WebResponse<Map<String, Any>>> {
                override fun onResponse(
                    call: Call<WebResponse<Map<String, Any>>>,
                    response: Response<WebResponse<Map<String, Any>>>,
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

                override fun onFailure(call: Call<WebResponse<Map<String, Any>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }

    fun getAllOrder(callback: (ApiResponse<WebResponse<List<OrderResponse>>>) -> Unit) {
        apiService.getAllOrders().enqueue(object : Callback<WebResponse<List<OrderResponse>>> {
            override fun onResponse(
                call: Call<WebResponse<List<OrderResponse>>>,
                response: Response<WebResponse<List<OrderResponse>>>,
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

            override fun onFailure(call: Call<WebResponse<List<OrderResponse>>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun getDetailOrder(
        orderId: String,
        callback: (ApiResponse<WebResponse<OrderResponse>>) -> Unit,
    ) {
        apiService.getDetailOrder(orderId).enqueue(object : Callback<WebResponse<OrderResponse>> {
            override fun onResponse(
                call: Call<WebResponse<OrderResponse>>,
                response: Response<WebResponse<OrderResponse>>,
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

            override fun onFailure(call: Call<WebResponse<OrderResponse>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun getAllCancelOrders(callback: (ApiResponse<WebResponse<List<OrderResponse>>>) -> Unit) {
        apiService.getAllCancelOrders()
            .enqueue(object : Callback<WebResponse<List<OrderResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<OrderResponse>>>,
                    response: Response<WebResponse<List<OrderResponse>>>,
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

                override fun onFailure(call: Call<WebResponse<List<OrderResponse>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }

    fun getAllCompleteOrders(callback: (ApiResponse<WebResponse<List<OrderResponse>>>) -> Unit) {
        apiService.getAllCompleteOrders()
            .enqueue(object : Callback<WebResponse<List<OrderResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<OrderResponse>>>,
                    response: Response<WebResponse<List<OrderResponse>>>,
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

                override fun onFailure(call: Call<WebResponse<List<OrderResponse>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }
            })
    }

    fun getAllPendingOrders(callback: (ApiResponse<WebResponse<List<OrderResponse>>>) -> Unit) {
        apiService.getAllPendingOrders()
            .enqueue(object : Callback<WebResponse<List<OrderResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<OrderResponse>>>,
                    response: Response<WebResponse<List<OrderResponse>>>,
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

                override fun onFailure(call: Call<WebResponse<List<OrderResponse>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }
            })
    }

}