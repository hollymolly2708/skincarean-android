package com.skincarean.android.core.data.domain.model

data class LoginUser(
    val token : String,
    val tokenCreatedAt : Long,
    val tokenExpiredAt : Long
)
