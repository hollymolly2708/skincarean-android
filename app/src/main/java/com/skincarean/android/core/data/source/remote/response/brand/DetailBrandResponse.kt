package com.skincarean.android.core.data.source.remote.response.brand

import com.google.gson.annotations.SerializedName

data class DetailBrandResponse(

	@field:SerializedName("brandPoster")
	val brandPoster: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("lastUpdatedAt")
	val lastUpdatedAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("contactEmailUrl")
	val contactEmailUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("facebookUrl")
	val facebookUrl: String? = null,

	@field:SerializedName("websiteMediaUrl")
	val websiteMediaUrl: String? = null,

	@field:SerializedName("isTopBrand")
	val isTopBrand: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("instagramUrl")
	val instagramUrl: String? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("brandLogo")
	val brandLogo: String? = null
)
