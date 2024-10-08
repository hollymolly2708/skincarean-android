package com.skincarean.android.core.data.source.remote.network

import com.skincarean.android.core.data.source.remote.request.GoogleTokenRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.response.VerificationResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/auth/login/google/verify")
    fun verifyToken(@Body token: GoogleTokenRequest): Call<VerificationResponse>

    @POST("api/users/auth/register")
    fun register(@Body registerUserRequest : RegisterUserRequest) : Call<WebResponse>

}