package com.skincarean.android.core.data.domain.usecase

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.PaymentMethod

interface PaymentMethodUseCase {
    fun getAllPaymentMethods(callback: (Resource<List<PaymentMethod>>) -> Unit)
}