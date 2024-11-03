package com.skincarean.android.core.data.domain.model.product

import java.math.BigDecimal

data class Product(
    val brandName: String? = null,
    val productId: String? = null,
    val productName: String? = null,
    val isPromo: Boolean? = null,
    val categoryName: String? = null,
    val isPopularProduct: Boolean? = null,
    val thumbnailImage: String? = null,
    val minPrice: BigDecimal? = null,
    val maxPrice: BigDecimal? = null,
    val firstPrice: BigDecimal? = null,
    val firstOriginalPrice: BigDecimal? = null,
    val firstDiscount: BigDecimal? = null,
)
