package com.skincarean.android.di

import com.skincarean.android.ui.ViewModelFactory

import com.skincarean.core.data.di.Injector.provideBrandInteractor
import com.skincarean.core.data.di.Injector.provideCartInteractor
import com.skincarean.core.data.di.Injector.provideOrderInteractor
import com.skincarean.core.data.di.Injector.providePaymentMethodInteractor
import com.skincarean.core.data.di.Injector.provideProductInteractor
import com.skincarean.core.data.di.Injector.provideUserInteractor

object Injector {
    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory.getInstance(
            provideUserInteractor(),
            providePaymentMethodInteractor(),
            provideBrandInteractor(),
            provideProductInteractor(),
            provideOrderInteractor(),
            provideCartInteractor()
        )
    }
}
