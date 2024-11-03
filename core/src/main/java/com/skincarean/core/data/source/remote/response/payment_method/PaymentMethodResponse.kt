package com.skincarean.android.core.data.source.remote.response.payment_method

import com.google.gson.annotations.SerializedName

data class PaymentMethodResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

