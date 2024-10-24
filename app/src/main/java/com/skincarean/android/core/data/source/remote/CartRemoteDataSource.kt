package com.skincarean.android.core.data.source.remote

import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        var INSTANCE: CartRemoteDataSource? = null
        fun getInstance(apiService: ApiService): CartRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = CartRemoteDataSource(apiService)
                }
            }
            return INSTANCE!!
        }
    }

    fun addProductToCart(
        cartRequest: CartRequest,
        callback: (ApiResponse<WebResponse<String>>) -> Unit,
        callbackCartResponse: (ApiResponse<WebResponse<CartResponse>>) -> Unit,
    ) {
        apiService.addProductToCart(cartRequest).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(ApiResponse.Success(body))
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
                    } else {
                        callback(ApiResponse.Error("Response body is null"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun getAllCarts(callback: (ApiResponse<WebResponse<CartResponse>>) -> Unit) {
        apiService.getAllCart().enqueue(object : Callback<WebResponse<CartResponse>> {
            override fun onResponse(
                call: Call<WebResponse<CartResponse>>,
                response: Response<WebResponse<CartResponse>>,
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

            override fun onFailure(call: Call<WebResponse<CartResponse>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun plusQuantity(
        cartId: Long,
        callback: (ApiResponse<WebResponse<String>>) -> Unit,
        callbackCartResponse: (ApiResponse<WebResponse<CartResponse>>) -> Unit,
    ) {

        apiService.plusQuantity(cartId).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(ApiResponse.Success(body))
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
                    } else {
                        callback(ApiResponse.Error("Response body is null"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun minusQuantity(
        cartId: Long,
        callback: (ApiResponse<WebResponse<String>>) -> Unit,
        callbackCartResponse: (ApiResponse<WebResponse<CartResponse>>) -> Unit,
    ) {
        apiService.minusQuantity(cartId).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(ApiResponse.Success(body))
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
                    } else {
                        callback(ApiResponse.Error("Response body is null"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun deleteCartItem(
        cartId: Long,
        callback: (ApiResponse<WebResponse<String>>) -> Unit,
        callbackCartResponse: (ApiResponse<WebResponse<CartResponse>>) -> Unit,
    ) {
        apiService.deleteCartItem(cartId).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(ApiResponse.Success(body))
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
                    } else {
                        callback(ApiResponse.Error("Response body is null"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun deleteAllCartItem(
        callback: (ApiResponse<WebResponse<String>>) -> Unit,
        callbackCartResponse: (ApiResponse<WebResponse<CartResponse>>) -> Unit,
    ) {
        apiService.deleteAllCartItem().enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(ApiResponse.Success(body))
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
                    } else {
                        callback(ApiResponse.Error("Response body is null"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }
}