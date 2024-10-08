package com.skincarean.android.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class LoginUserResponse(
    @field:SerializedName("token")
    val token: String,
    @field:SerializedName("tokenCreatedAt")
    val tokenCreatedAt: Long,
    @field:SerializedName("tokenExpiredAt")
    val tokenExpiredAt: Long,
)
