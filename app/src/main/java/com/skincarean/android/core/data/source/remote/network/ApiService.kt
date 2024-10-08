package com.skincarean.android.core.data.source.remote.network

import com.skincarean.android.core.data.source.remote.request.GoogleTokenRequest
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.response.LoginUserResponse
import com.skincarean.android.core.data.source.remote.response.VerificationResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/auth/login/google/verify")
    fun verifyToken(@Body token: GoogleTokenRequest): Call<WebResponse<LoginUserResponse>>

    @POST("api/users/auth/register")
    fun register(@Body registerUserRequest: RegisterUserRequest): Call<WebResponse<String>>

    @POST("api/users/auth/login")
    fun login(@Body loginUserRequest: LoginUserRequest): Call<WebResponse<LoginUserResponse>>
}