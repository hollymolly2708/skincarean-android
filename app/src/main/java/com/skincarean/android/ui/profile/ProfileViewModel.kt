package com.skincarean.android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.user.User
import com.skincarean.android.core.data.domain.usecase.user.UserUseCase
import com.skincarean.android.core.data.repository.UserRepository

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    private val _message: MutableLiveData<String> = MutableLiveData()

    val message: LiveData<String> = _message
    val currentUser: LiveData<User> = _currentUser

    fun getCurrentUser() {
        userUseCase.getCurrentUser { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _currentUser.value = it
                    }
                    resource.message.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    resource.message.let {
                        _message.value = it
                    }
                }
            }
        }
    }

    fun updateUser(fullName: String, address: String, phone: String, email: String) {
        userUseCase.updateUser(fullName, address, email, phone) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _currentUser.value = it
                    }
                    resource.message.let {
                        _message.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }

        }
    }

    fun logout() {
        userUseCase.logoutUser { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _message.value = it
                    }
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }

        }
    }
}