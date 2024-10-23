package com.skincarean.android.core.data.source.remote.response.login

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("lastUpdatedAt")
	val lastUpdatedAt: String? = null,

	@field:SerializedName("tokenCreatedAt")
	val tokenCreatedAt: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("tokenExpiredAt")
	val tokenExpiredAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
