package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.PaymentMethodRemoteDataSource

class PaymentMethodRepository private constructor(private val paymentMethodRemoteDataSource: PaymentMethodRemoteDataSource) {
    companion object {
        @Volatile
        var INSTANCE: PaymentMethodRepository? = null
        fun getInstance(paymentMethodRemoteDataSource: PaymentMethodRemoteDataSource) : PaymentMethodRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = PaymentMethodRepository(paymentMethodRemoteDataSource)
                }
            }
            return INSTANCE!!
        }
    }
    fun getAllPaymentMethods(callback: (Any) -> Unit) {
        paymentMethodRemoteDataSource.getAllPaymentMethods(callback)
    }
}