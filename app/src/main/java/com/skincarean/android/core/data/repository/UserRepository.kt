package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.RemoteDataSource
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse


class UserRepository private constructor(private val remoteDataSource: RemoteDataSource) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(remoteDataSource: RemoteDataSource): UserRepository {
            if (instance == null) {
                synchronized(this) {
                    instance = UserRepository(remoteDataSource)
                }
            }
            return instance!!
        }
    }


    fun registerUser(
        createRegisterUserRequest: RegisterUserRequest,
        callback: (Any) -> Unit,
    ) {
        remoteDataSource.register(createRegisterUserRequest, callback)
    }

}