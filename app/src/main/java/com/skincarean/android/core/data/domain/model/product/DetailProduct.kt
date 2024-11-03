package com.skincarean.android.core.data.domain.model.product

import java.math.BigDecimal

data class DetailProduct(
    val brandName: String? = null,
    val productId: String? = null,
    val productName : String? = null,
    val totalStok: Long? = null,
    val isPromo: Boolean? = null,
    val categoryName: String? = null,
    val bpomCode: String? = null,
    val isPopularProduct: Boolean? = null,
    val thumbnailImage: String? = null,
    val minPrice : BigDecimal? = null,
    val maxPrice : BigDecimal? = null,
    val ingredient : String? = null,
    val productDescription: String? = null,
    val productVariants : List<ProductVariant?>? = null,
    val productVariant : ProductVariant? = null
)
