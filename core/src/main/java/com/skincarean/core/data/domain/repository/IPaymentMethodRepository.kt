package com.skincarean.android.core.data.domain.repository



import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.core.Resource

interface IPaymentMethodRepository {
    fun getAllPaymentMethods(callback : (Resource<List<PaymentMethod>>) -> Unit)
}