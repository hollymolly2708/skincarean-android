package com.skincarean.android.core.data.source.remote.response.cart

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class CartResponse(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: BigDecimal? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("cartItems")
	val cartItems: List<CartItemResponse?>? = null
)

