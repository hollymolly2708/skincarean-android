package com.skincarean.android.core.data.source.remote.response.order

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PaymentResponse(

    @field:SerializedName("paymentCode")
    val paymentCode: String? = null,

    @field:SerializedName("paidDate")
    val paidDate: Any? = null,

    @field:SerializedName("totalPaid")
    val totalPaid: BigDecimal,

    @field:SerializedName("paymentMethodId")
    val paymentMethodId: Int? = null,

    @field:SerializedName("paymentStatus")
    val paymentStatus: String? = null
)