package com.skincarean.android.core.data.domain.model.product

data class Review(
    val createdAt: String? = null,
    val review: String? = null,
    val photoProfileUser: String? = null,
    val isRecommended: Boolean? = null,
    val rating: Int? = null,
    val usagePeriod: String? = null,
    val fullNameUser: String? = null,
    val reviewId: Int? = null
)
