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

    fun getAllPaymentMethods(callback: (Any) -> Unit) {
        apiService.getPaymentMethods()
            .enqueue(object : Callback<WebResponse<List<PaymentMethodResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<PaymentMethodResponse>>>,
                    response: Response<WebResponse<List<PaymentMethodResponse>>>,
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
                            val responseErrorBody =
                                gson.fromJson(errorBody, WebResponse::class.java)
                            callback(responseErrorBody)
                            val responseErrorBodyFromServer =
                                gson.fromJson(errorBody, ErrorResponse::class.java)
                            callback(responseErrorBodyFromServer)
                        } else {
                            callback(WebResponse(null, null, "Response error body is null", false))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<WebResponse<List<PaymentMethodResponse>>>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    callback(WebResponse(null, null, t.message, false))
                }


            })
    }
}