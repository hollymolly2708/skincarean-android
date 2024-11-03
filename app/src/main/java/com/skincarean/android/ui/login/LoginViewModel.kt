package com.skincarean.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skincarean.core.Resource
import com.skincarean.android.core.data.domain.model.user.LoginUser
import com.skincarean.android.core.data.domain.usecase.user.UserUseCase
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest

class LoginViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _loginResult: MutableLiveData<LoginUser> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _loading : MutableLiveData<Boolean> = MutableLiveData()


    val errorMessage: LiveData<String> = _errorMessage
    val loginResult: LiveData<LoginUser> = _loginResult
    val loading : LiveData<Boolean> = _loading
    fun login(loginUserRequest: LoginUserRequest) {
        userUseCase.loginUser(loginUserRequest) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _loginResult.value = it
                    }

                    _loading.value = false

                }

                is Resource.Loading -> {
                    _loading.value = true
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _errorMessage.value = it
                    }

                    _loading.value = false
                }


            }
        }
    }

    fun loginViaGoogle(idToken: String?) {
        userUseCase.loginViaGoogle(idToken = idToken) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data.let {
                        _loginResult.value = it
                    }
                    _loading.value = false
                }

                is Resource.Error -> {
                    resource.message?.let {
                        _errorMessage.value = it
                    }
                    _loading.value = false
                }

                is Resource.Loading -> {
                    _loading.value = true
                }
            }
        }
    }

}
