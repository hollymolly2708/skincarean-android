package com.skincarean.android.core.data.domain.usecase.payment_method

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.android.core.data.repository.PaymentMethodRepository

class PaymentMethodInteractor(private val paymentMethodRepository: PaymentMethodRepository) :
    PaymentMethodUseCase {
    override fun getAllPaymentMethods(callback: (Resource<List<PaymentMethod>>) -> Unit) {
        paymentMethodRepository.getAllPaymentMethods(callback)
    }
}