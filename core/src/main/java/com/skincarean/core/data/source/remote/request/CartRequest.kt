package com.skincarean.android.core.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CartRequest(
    val productId: String,
    val quantity: Long,
    val productVariantId : Long
)
