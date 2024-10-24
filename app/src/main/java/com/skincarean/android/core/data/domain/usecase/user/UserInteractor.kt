package com.skincarean.android.core.data.domain.usecase.user

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.user.LoginUser
import com.skincarean.android.core.data.domain.model.user.User
import com.skincarean.android.core.data.domain.repository.IUserRepository
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun loginUser(
        loginUserRequest: LoginUserRequest,
        callback: (Resource<LoginUser>) -> Unit,
    ) {
        userRepository.loginUser(loginUserRequest, callback)
    }

    override fun loginViaGoogle(
        idToken: String?,
        callback: (Resource<LoginUser>) -> Unit,
    ) {
        userRepository.loginViaGoogle(idToken, callback)
    }

    override fun registerUser(
        registerUserRequest: RegisterUserRequest,
        callback: (Resource<String>) -> Unit,
    ) {
        userRepository.registerUser(registerUserRequest, callback)
    }

    override fun getCurrentUser(callback: (Resource<User>) -> Unit) {
        userRepository.getCurrentUser(callback)
    }

    override fun updateUser(
        fullName: String,
        address: String,
        email: String,
        phone: String,
        callback: (Resource<User>) -> Unit,
    ) {
        userRepository.updateUser(fullName, address, email, phone, callback)
    }

    override fun logoutUser(callback: (Resource<String>) -> Unit) {
        userRepository.logoutUser(callback)
    }

}