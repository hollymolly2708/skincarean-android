package com.skincarean.android.core.data.domain.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.user.LoginUser
import com.skincarean.android.core.data.domain.model.user.User
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest

interface IUserRepository {
    fun loginUser(loginUserRequest: LoginUserRequest, callback: (Resource<LoginUser>) -> Unit)
    fun loginViaGoogle(idToken: String?, callback: (Resource<LoginUser>) -> Unit)
    fun registerUser(registerUserRequest: RegisterUserRequest, callback: (Resource<String>) -> Unit)
    fun getCurrentUser(callback: (Resource<User>) -> Unit)
    fun updateUser(
        fullName: String,
        address: String,
        email: String,
        phone: String,
        callback: (Resource<User>) -> Unit,
    )

    fun logoutUser(callback: (Resource<String>) -> Unit)
}