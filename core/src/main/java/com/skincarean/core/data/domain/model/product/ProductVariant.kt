package com.skincarean.android.core.data.domain.model.product

import java.math.BigDecimal

data class ProductVariant(
    val id: Long? = null,
    val size: String? = null,
    val stok: Long? = null,
    val price: BigDecimal? = null,
    val originalPrice: BigDecimal? = null,
    val discount: BigDecimal? = null,
    val productImageItems: List<ProductImageItem?>? = null,
    val thumbnailVariantImage : String? = null,
    var isSelected: Boolean = false

)