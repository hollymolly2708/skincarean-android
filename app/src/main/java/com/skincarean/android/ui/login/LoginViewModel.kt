package com.skincarean.android.ui.login

import android.media.metrics.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.LoginUserResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResult: MutableLiveData<LoginUserResponse> = MutableLiveData()
    val loginResult: LiveData<LoginUserResponse> = _loginResult

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(loginUserRequest: LoginUserRequest) {
        userRepository.login(loginUserRequest) { response ->
            when (response) {
                is WebResponse<*> -> {
                    response.data?.let {
                        _loginResult.value = it as LoginUserResponse
                    }
                    response.errors?.let {
                        _errorMessage.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _errorMessage.value = it
                    }
                }

            }
        }
    }

    fun loginViaGoogle(idToken: String?) {
        userRepository.loginViaGoogle(idToken = idToken) { response ->
            when (response) {
                is WebResponse<*> -> {

                    response.data?.let {
                        _loginResult.value = it as LoginUserResponse
                    }
                    response.errors?.let {
                        _errorMessage.value = it
                    }
                }

                is ErrorResponse -> {
                    response.error?.let {
                        _errorMessage.value = it
                    }
                }
            }
        }
    }
}