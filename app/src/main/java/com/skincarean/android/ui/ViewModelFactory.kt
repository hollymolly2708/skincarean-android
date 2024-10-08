package com.skincarean.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.ui.register.RegisterViewModel


class ViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(userRepository: UserRepository): ViewModelFactory {
            synchronized(this) {
                instance = ViewModelFactory(userRepository)
            }

            return instance!!
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(userRepository) as T
            }

            else -> {
                throw Throwable("Unknown Viewmodel class : " + modelClass.name)
            }
        }
    }

}