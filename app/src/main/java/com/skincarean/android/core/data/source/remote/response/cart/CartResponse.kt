package com.skincarean.android.core.data.source.remote.response.cart

import com.google.gson.annotations.SerializedName


data class CartResponse(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: Any? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("cartItems")
	val cartItems: List<CartItemResponse?>? = null
)

