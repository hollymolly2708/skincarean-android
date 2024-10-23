package com.skincarean.android.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.usecase.UserUseCase
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.event.Event
import kotlin.math.E

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

//    fun registerUser(registerUserRequest: RegisterUserRequest) {
//        userRepository.registerUser(registerUserRequest) { response ->
//
//
//            // Cek jika response merupakan WebResponse
//            if (response is WebResponse<*>) {
//                // Jika ada data, set nilai registerResult
//                response.data?.let {
//                    _registerResult.value = Event(it.toString())
//                }
//
//                // Jika ada error, set nilai errorMessage
//                response.errors?.let {
//                    _errorMessage.value = Event(it)
//
//                }
//
//                Log.d("registerViewModel", response.errors.toString())
//            } else if (response is ErrorResponse) {
//                // Tangani ErrorResponse
//                response.error?.let {
//                    _errorMessage.value = Event(it)
//                    Log.d("registerViewModel", response.error)
//                }
//            }
//        }
//
//    }
}
