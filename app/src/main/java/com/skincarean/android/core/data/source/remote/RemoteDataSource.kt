package com.skincarean.android.core.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.request.GoogleTokenRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.VerificationResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(apiService: ApiService): RemoteDataSource {
            if (instance == null) {
                synchronized(this) {
                    instance = RemoteDataSource(apiService)
                }
            }
            return instance!!
        }
    }

    fun loginViaGoogle(idToken: String?) {
        apiService.verifyToken(GoogleTokenRequest(idToken!!)).enqueue(object :
            Callback<VerificationResponse> {
            override fun onResponse(
                call: Call<VerificationResponse>,
                response: Response<VerificationResponse>,
            ) {
                if (response.isSuccessful) {
                    // Token valid, lanjutkan dengan login


                } else {
                    // Token tidak valid
                    Log.d("responseFailure", "${response.body()}")
                }
            }

            override fun onFailure(call: Call<VerificationResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun register(
        registerUserRequest: RegisterUserRequest,
        callback: (Any) -> Unit,
    ) {
        apiService.register(registerUserRequest).enqueue(object : Callback<WebResponse> {
            override fun onResponse(
                call: Call<WebResponse>,
                response: Response<WebResponse>,
            ) {
                if (response.isSuccessful) {
                    val webResponse = response.body()
                    if (webResponse != null) {
                        callback(webResponse)
                    } else {
                        Log.e("Response Error", "Body is null")
                        callback(WebResponse(null, "Error: Response body is null", null, false))
                    }
                } else {
                    Log.e("Response Error", "Response unsuccessful")
                    val errorBody = response.errorBody()?.string()
                    Log.d("errorBody", errorBody.toString())
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, WebResponse::class.java)
                    val errorResponseFromServer =
                        gson.fromJson(errorBody, ErrorResponse::class.java)

                    Log.d("errorResponse", errorResponse.toString())
                    callback(errorResponse)

                    Log.d("errorResponseFromServer", errorResponseFromServer.toString())
                    callback(errorResponseFromServer)

                    
                }

            }

            override fun onFailure(call: Call<WebResponse>, t: Throwable) {

            }

        })

    }


}