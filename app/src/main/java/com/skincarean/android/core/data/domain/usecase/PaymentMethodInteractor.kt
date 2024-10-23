package com.skincarean.android.core.data.domain.usecase

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.PaymentMethod
import com.skincarean.android.core.data.repository.PaymentMethodRepository

class PaymentMethodInteractor(private val paymentMethodRepository: PaymentMethodRepository) :
    PaymentMethodUseCase {
    override fun getAllPaymentMethods(callback: (Resource<List<PaymentMethod>>) -> Unit) {
        paymentMethodRepository.getAllPaymentMethods(callback)
    }
}