package com.skincarean.android.core.data.repository


import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.PaymentMethod
import com.skincarean.android.core.data.domain.repository.IPaymentMethodRepository
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.PaymentMethodRemoteDataSource
import java.util.stream.Collectors

class PaymentMethodRepository private constructor(private val paymentMethodRemoteDataSource: PaymentMethodRemoteDataSource) :
    IPaymentMethodRepository {
    companion object {
        @Volatile
        var INSTANCE: PaymentMethodRepository? = null
        fun getInstance(paymentMethodRemoteDataSource: PaymentMethodRemoteDataSource): PaymentMethodRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = PaymentMethodRepository(paymentMethodRemoteDataSource)
                }
            }
            return INSTANCE!!
        }
    }

    override fun getAllPaymentMethods(callback: (Resource<List<PaymentMethod>>) -> Unit) {
        paymentMethodRemoteDataSource.getAllPaymentMethods { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val listPaymentMethod = apiResponse.data.data?.let { paymentMethodResponses ->
                        paymentMethodResponses.stream().map { it ->
                            PaymentMethod(
                                it.image, it.id, it.description, it.name
                            )
                        }.collect(Collectors.toList())
                    }
                    callback(Resource.Success(listPaymentMethod))
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }


//    fun getAllPaymentMethods(callback: (Any) -> Unit) {
//        paymentMethodRemoteDataSource.getAllPaymentMethods(callback)
//    }
}