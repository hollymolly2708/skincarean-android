package com.skincarean.android.core.data.source.remote.request

data class UpdateUserRequest(
    val fullName: String,
    val address: String,
    val phone: String,
    val email: String,
)
