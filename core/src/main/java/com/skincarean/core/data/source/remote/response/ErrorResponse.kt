package com.skincarean.android.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
