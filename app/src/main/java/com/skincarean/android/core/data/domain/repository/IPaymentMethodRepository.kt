package com.skincarean.android.core.data.domain.repository


import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.PaymentMethod

interface IPaymentMethodRepository {
    fun getAllPaymentMethods(callback : (Resource<List<PaymentMethod>>) -> Unit)
}