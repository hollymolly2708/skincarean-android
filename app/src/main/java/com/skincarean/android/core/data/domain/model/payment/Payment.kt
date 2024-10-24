package com.skincarean.android.core.data.domain.model.payment

import java.math.BigDecimal

data class Payment(
    val paymentCode: String? = null,
    val paidDate: Any? = null,
    val totalPaid: BigDecimal? = null,
    val paymentMethodId: Int? = null,
    val paymentStatus: String? = null,
    val paymentMethodName : String? = null
)
