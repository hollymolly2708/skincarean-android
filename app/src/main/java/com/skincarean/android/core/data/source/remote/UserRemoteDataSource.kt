package com.skincarean.android.core.data.source.remote

import android.util.Log
import com.google.android.gms.common.api.Api
import com.google.gson.Gson
import com.skincarean.android.core.data.domain.model.LoginUser
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.core.data.source.remote.request.GoogleTokenRequest
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.request.UpdateUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.login.LoginUserResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.login.UserResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: UserRemoteDataSource? = null
        fun getInstance(apiService: ApiService): UserRemoteDataSource {
            if (instance == null) {
                synchronized(this) {
                    instance = UserRemoteDataSource(apiService)
                }
            }
            return instance!!
        }
    }

    fun loginViaGoogle(
        idToken: String?,
        callback: (ApiResponse<WebResponse<LoginUserResponse>>) -> Unit,
    ) {
        apiService.verifyToken(GoogleTokenRequest(idToken!!)).enqueue(object :
            Callback<WebResponse<LoginUserResponse>> {
            override fun onResponse(
                call: Call<WebResponse<LoginUserResponse>>,
                response: Response<WebResponse<LoginUserResponse>>,
            ) {
                if (response.isSuccessful) {

                    val body = response.body()
                    if (body != null) {
                        callback(ApiResponse.Success(body))
                    } else {
                        callback(
                            ApiResponse.Error("Response body is null")
                        )
                    }

                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(ApiResponse.Error(errorBody ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<WebResponse<LoginUserResponse>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }
        })
    }

    fun register(
        registerUserRequest: RegisterUserRequest,
        callback: (ApiResponse<WebResponse<String>>) -> Unit,
    ) {
        apiService.register(registerUserRequest).enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
            ) {
                if (response.isSuccessful) {
                    val webResponse = response.body()
                    if (webResponse != null) {
                        callback(ApiResponse.Success(webResponse))
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

    fun login(
        loginUserRequest: LoginUserRequest,
        callback: (ApiResponse<WebResponse<LoginUserResponse>>) -> Unit,
    ) {
        apiService.login(loginUserRequest)
            .enqueue(object : Callback<WebResponse<LoginUserResponse>> {
                override fun onResponse(
                    call: Call<WebResponse<LoginUserResponse>>,
                    response: Response<WebResponse<LoginUserResponse>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback(ApiResponse.Success(body))
                        } else {
                            Log.e("loginResponse", "Body is null")
                            callback(
                                ApiResponse.Error("Response body is null")
                            )
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        callback(ApiResponse.Error(errorBody ?: "Unknown Error"))
                    }
                }

                override fun onFailure(call: Call<WebResponse<LoginUserResponse>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }

    fun getCurrentUser(callback: (ApiResponse<WebResponse<UserResponse>>) -> Unit) {
        apiService.getCurrentUser().enqueue(object : Callback<WebResponse<UserResponse>> {
            override fun onResponse(
                call: Call<WebResponse<UserResponse>>,
                response: Response<WebResponse<UserResponse>>,
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

            override fun onFailure(call: Call<WebResponse<UserResponse>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }

    fun updateUser(
        fullName: String,
        address: String,
        email: String,
        phone: String,
        callback: (ApiResponse<WebResponse<UserResponse>>) -> Unit,
    ) {
        apiService.updateUser(fullName, address, phone, email)
            .enqueue(object : Callback<WebResponse<UserResponse>> {
                override fun onResponse(
                    call: Call<WebResponse<UserResponse>>,
                    response: Response<WebResponse<UserResponse>>,
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

                override fun onFailure(call: Call<WebResponse<UserResponse>>, t: Throwable) {
                    t.printStackTrace()
                    callback(ApiResponse.Error(t.message.toString()))
                }

            })
    }

    fun logout(callback: (ApiResponse<WebResponse<String>>) -> Unit) {
        apiService.logout().enqueue(object : Callback<WebResponse<String>> {
            override fun onResponse(
                call: Call<WebResponse<String>>,
                response: Response<WebResponse<String>>,
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

            override fun onFailure(call: Call<WebResponse<String>>, t: Throwable) {
                t.printStackTrace()
                callback(ApiResponse.Error(t.message.toString()))
            }

        })
    }


}