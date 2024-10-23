package com.skincarean.android.core.data.repository

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.LoginUser
import com.skincarean.android.core.data.domain.model.User
import com.skincarean.android.core.data.domain.repository.IUserRepository
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.UserRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest


class UserRepository private constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    IUserRepository {
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


//    fun getCurrentUser(callback: (Any) -> Unit) {
//        userRemoteDataSource.getCurrentUser(callback)
//    }


//    fun updateUser(
//        fullName: String,
//        address: String,
//        email: String,
//        phone: String,
//        callback: (Any) -> Unit,
//    ) {
//        userRemoteDataSource.updateUser(fullName, address, email, phone, callback)
//    }
//
//    fun logoutUser(callback: (Any) -> Unit) {
//        userRemoteDataSource.logout(callback)
//    }

    override fun loginUser(
        loginUserRequest: LoginUserRequest,
        callback: (Resource<LoginUser>) -> Unit,
    ) {
        userRemoteDataSource.login(loginUserRequest) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data.data?.let { loginUserResponse ->
                        val loginUser = LoginUser(
                            loginUserResponse.token,
                            loginUserResponse.tokenCreatedAt,
                            loginUserResponse.tokenExpiredAt
                        )
                        callback(Resource.Success(loginUser))
                    }
                }

                is ApiResponse.Error -> {
                    response.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun loginViaGoogle(idToken: String?, callback: (Resource<LoginUser>) -> Unit) {
        userRemoteDataSource.loginViaGoogle(idToken) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        callback(
                            Resource.Success(
                                LoginUser(
                                    it.token, it.tokenCreatedAt, it.tokenExpiredAt
                                )
                            )
                        )
                    }
                }

                is ApiResponse.Error -> {
                    callback(Resource.Error(apiResponse.errorMessage))
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data  available"))
                }
            }
        }
    }

    override fun registerUser(
        registerUserRequest: RegisterUserRequest,
        callback: (Resource<String>) -> Unit,
    ) {
        userRemoteDataSource.register(registerUserRequest) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data.let {
                        callback(Resource.Success(it))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun getCurrentUser(callback: (Resource<User>) -> Unit) {
        userRemoteDataSource.getCurrentUser { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        val userResponseToUser = User(
                            it.profilePicture,
                            it.createdAt,
                            it.address,
                            it.phone,
                            it.lastUpdatedAt,
                            it.tokenCreatedAt,
                            it.fullName,
                            it.tokenExpiredAt,
                            it.email,
                            it.username,
                            it.token
                        )
                        callback(Resource.Success(userResponseToUser))
                    }

                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun updateUser(
        fullName: String,
        address: String,
        email: String,
        phone: String,
        callback: (Resource<User>) -> Unit,
    ) {
        userRemoteDataSource.updateUser(fullName, address, email, phone) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        callback(
                            Resource.Success(
                                User(
                                    it.profilePicture,
                                    it.createdAt,
                                    it.address,
                                    it.phone,
                                    it.lastUpdatedAt,
                                    it.tokenCreatedAt,
                                    it.fullName,
                                    it.tokenExpiredAt,
                                    it.email,
                                    it.username,
                                    it.token
                                )
                            )
                        )
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun logoutUser(callback: (Resource<String>) -> Unit) {
        userRemoteDataSource.logout { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        callback(Resource.Success(it))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }


}