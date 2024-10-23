package com.skincarean.android.core.data.source.remote

import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PaymentMethodRemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        var INSTANCE: PaymentMethodRemoteDataSource? = null

        fun getInstance(apiService: ApiService): PaymentMethodRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = PaymentMethodRemoteDataSource(apiService)
                }

            }
            return INSTANCE!!
        }
    }

    fun getAllPaymentMethods(callback: (ApiResponse<WebResponse<List<PaymentMethodResponse>>>) -> Unit) {
        apiService.getPaymentMethods()
            .enqueue(object : Callback<WebResponse<List<PaymentMethodResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<PaymentMethodResponse>>>,
                    response: Response<WebResponse<List<PaymentMethodResponse>>>,
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
                    call: Call<WebResponse<List<PaymentMethodResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }


            })
    }
}