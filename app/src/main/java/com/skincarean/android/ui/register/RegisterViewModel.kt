package com.skincarean.android.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.usecase.user.UserUseCase
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.event.Event

class RegisterViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _registerResult: MutableLiveData<Event<String>> = MutableLiveData()
    val registerResult: LiveData<Event<String>> = _registerResult

    private val _errorMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun registerUser(registerUserRequest: RegisterUserRequest) {
        userUseCase.registerUser(registerUserRequest) { resource ->

            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _registerResult.value = Event(it.toString())
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    resource.message?.let {
                        _errorMessage.value = Event(it)
                    }
                }
            }
        }
    }


}
