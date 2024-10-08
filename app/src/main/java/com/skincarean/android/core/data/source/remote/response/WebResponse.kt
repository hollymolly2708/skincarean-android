package com.skincarean.android.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class WebResponse(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("paging")
	val paging: Any? = null,

	@field:SerializedName("errors")
	val errors: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)
