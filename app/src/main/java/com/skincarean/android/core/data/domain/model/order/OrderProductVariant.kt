package com.skincarean.android.core.data.domain.model.order

import java.math.BigDecimal

data class OrderProductVariant(
    val id: Long? = null,
    val size: String? = null,
    val price: BigDecimal? = null,
    val originalPrice: BigDecimal? = null,
    val discount: BigDecimal? = null,
    val thumbnailImageVariant : String? = null
)
