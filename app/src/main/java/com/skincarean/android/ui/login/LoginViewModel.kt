package com.skincarean.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.LoginUser
import com.skincarean.android.core.data.domain.usecase.UserUseCase
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.response.ErrorResponse
import com.skincarean.android.core.data.source.remote.response.login.LoginUserResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse

class LoginViewModel(
    private val userRepository: UserRepository,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _loginResult: MutableLiveData<LoginUser> = MutableLiveData()
    val loginResult: LiveData<LoginUser> = _loginResult

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(loginUserRequest: LoginUserRequest) {
        userUseCase.loginUser(loginUserRequest) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _loginResult.value = it
                    }

                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    resource.message?.let {
                        _errorMessage.value = it
                    }
                }


            }
        }
    }

    fun loginViaGoogle(idToken: String?) {
        userRepository.loginViaGoogle(idToken = idToken) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _loginResult.value = it
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _errorMessage.value = it
                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

}
