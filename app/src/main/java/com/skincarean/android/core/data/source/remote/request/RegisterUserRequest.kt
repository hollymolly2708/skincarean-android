package com.skincarean.android.core.data.source.remote.request

data class RegisterUserRequest(
    val username : String,
    val password : String,
    val confirmPassword : String,
    val email : String,
    val phone : String,
    val address : String,
    val fullName : String
)
