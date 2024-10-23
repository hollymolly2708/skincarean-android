package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.UserRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest


class UserRepository private constructor(private val userRemoteDataSource: UserRemoteDataSource) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(userRemoteDataSource: UserRemoteDataSource): UserRepository {
            if (instance == null) {
                synchronized(this) {
                    instance = UserRepository(userRemoteDataSource)
                }
            }
            return instance!!
        }
    }

     fun loginUser(loginUserRequest: LoginUserRequest, callback: (Any) -> Unit) {
        userRemoteDataSource.login(loginUserRequest, callback)
    }

     fun registerUser(registerUserRequest: RegisterUserRequest, callback: (Any) -> Unit) {
        userRemoteDataSource.register(registerUserRequest, callback)
    }

     fun getCurrentUser(callback: (Any) -> Unit) {
        userRemoteDataSource.getUserProfile(callback)
    }

     fun loginViaGoogle(idToken: String?, callback: (Any) -> Unit) {
        userRemoteDataSource.loginViaGoogle(idToken, callback)
    }

     fun updateUser(
        fullName: String,
        address: String,
        email: String,
        phone: String,
        callback: (Any) -> Unit,
    ) {
        userRemoteDataSource.updateUser(fullName, address, email, phone, callback)
    }

     fun logoutUser(callback: (Any) -> Unit) {
        userRemoteDataSource.logout(callback)
    }


}