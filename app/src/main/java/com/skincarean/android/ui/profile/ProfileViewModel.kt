package com.skincarean.android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.core.data.source.remote.request.UpdateUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.login.UserResponse

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _currentUserResponse: MutableLiveData<UserResponse> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()

    val message: LiveData<String> = _message
    val currentUserResponse: LiveData<UserResponse> = _currentUserResponse

    fun getCurrentUser() {
        userRepository.getCurrentUser { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _currentUserResponse.value = it as UserResponse
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }
        }
    }

    fun updateUser(fullName: String, address: String, phone: String, email: String) {
        userRepository.updateUser(fullName, address, phone, email) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _currentUserResponse.value = it as UserResponse
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }
        }
    }

    fun logout() {
        userRepository.logoutUser { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _message.value = it as String
                    }
                    response.errors?.let {
                        _message.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _message.value = it
                    }
                }
            }

        }
    }
}