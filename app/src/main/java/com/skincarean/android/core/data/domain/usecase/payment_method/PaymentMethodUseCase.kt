package com.skincarean.android.core.data.domain.usecase.payment_method

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod

interface PaymentMethodUseCase {
    fun getAllPaymentMethods(callback: (Resource<List<PaymentMethod>>) -> Unit)
}