package com.skincarean.android.core.data.source.remote

import com.google.gson.Gson
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
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

    fun getallBrandByTopBrands(callback: (Any) -> Unit) {

        apiService.getBrandsByTopBrand()
            .enqueue(object : Callback<WebResponse<List<BrandResponse>>> {
                override fun onResponse(
                    call: Call<WebResponse<List<BrandResponse>>>,
                    response: Response<WebResponse<List<BrandResponse>>>,
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
                            callback(errorResponse)
                            val errorResponseFromServer =
                                gson.fromJson(errorBody, ErrorResponse::class.java)
                            callback(errorResponseFromServer)
                        } else {
                            callback(WebResponse(null, null, "Response error body is null", false))
                        }
                    }
                }

                override fun onFailure(call: Call<WebResponse<List<BrandResponse>>>, t: Throwable) {
                    t.printStackTrace()
                    callback(WebResponse(null, null, t.message, false))
                }

            })

    }
}

