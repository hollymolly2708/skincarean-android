package com.skincarean.android.core.data.domain.usecase.payment_method


import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.core.Resource

interface PaymentMethodUseCase {
    fun getAllPaymentMethods(callback: (Resource<List<PaymentMethod>>) -> Unit)
}