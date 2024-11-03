package com.skincarean.android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.core.Resource
import com.skincarean.android.core.data.domain.model.user.User
import com.skincarean.android.core.data.domain.usecase.user.UserUseCase
import com.skincarean.android.event.Event

class ProfileViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    private val _message: MutableLiveData<Event<String>> = MutableLiveData()
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()

    val loading: LiveData<Boolean> = _loading
    val message: LiveData<Event<String>> = _message
    val currentUser: LiveData<User> = _currentUser

    fun getCurrentUser() {
        userUseCase.getCurrentUser { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _currentUser.value = it
                    }
                    _loading.value = false
                }

                is Resource.Loading -> {
                    _loading.value = true
                }

                is Resource.Error -> {
                    resource.message.let {
                        _message.value = Event(it.toString())
                    }
                    _loading.value = false
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
                        _message.value = Event(it.toString())
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = Event(it)
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
                        _message.value = Event(it)
                    }
                    resource.message?.let {
                        _message.value = Event(it)
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _message.value = Event(it)
                    }
                }

                is Resource.Loading -> {

                }
            }

        }
    }
}