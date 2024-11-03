package com.skincarean.android.core.data.domain.model.cart

import java.math.BigDecimal

data class CartProductVariant(
    val id: Long? = null,
    val size: String? = null,
    val price: BigDecimal? = null,
    val originalPrice: BigDecimal? = null,
    val discount: BigDecimal? = null,
    val thumbnailVariantImage: String? = null,
)
