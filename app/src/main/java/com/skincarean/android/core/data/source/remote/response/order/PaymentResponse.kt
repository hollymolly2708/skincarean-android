package com.skincarean.android.core.data.source.remote.response.order

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

    @field:SerializedName("paymentCode")
    val paymentCode: String? = null,

    @field:SerializedName("paidDate")
    val paidDate: Any? = null,

    @field:SerializedName("totalPaid")
    val totalPaid: Any? = null,

    @field:SerializedName("paymentMethodId")
    val paymentMethodId: Int? = null,

    @field:SerializedName("paymentStatus")
    val paymentStatus: String? = null
)