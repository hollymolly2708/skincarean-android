package com.skincarean.android.core.data.source.remote

import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
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
        callback: (Any) -> Unit,
        callbackCartResponse: (Any) -> Unit,
    ) {
        apiService.addProductToCart(cartRequest).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(body)
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
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

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(WebResponse(null, null, t.message, false))
            }

        })
    }

    fun getAllCarts(callback: (Any) -> Unit) {
        apiService.getAllCart().enqueue(object : Callback<WebResponse<CartResponse>> {
            override fun onResponse(
                call: Call<WebResponse<CartResponse>>,
                response: Response<WebResponse<CartResponse>>,
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

            override fun onFailure(call: Call<WebResponse<CartResponse>>, t: Throwable) {
                t.printStackTrace()
                callback(WebResponse(null, null, t.message, false))
            }

        })
    }

    fun plusQuantity(cartId: Long, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {

        apiService.plusQuantity(cartId).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(body)
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
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

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(WebResponse(null, null, t.message, false))
            }

        })
    }

    fun minusQuantity(cartId: Long, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        apiService.minusQuantity(cartId).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(body)
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
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

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(WebResponse(null, null, t.message, false))
            }

        })
    }

    fun deleteCartItem(cartId: Long, callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        apiService.deleteCartItem(cartId).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(body)
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
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

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(WebResponse(null, null, t.message, false))
            }

        })
    }

    fun deleteAllCartItem(callback: (Any) -> Unit, callbackCartResponse: (Any) -> Unit) {
        apiService.deleteAllCartItem().enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback(body)
                        getAllCarts { cartResponse -> callbackCartResponse(cartResponse) }
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

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(WebResponse(null, null, t.message, false))
            }

        })
    }
}